package igorilin13.com.github.main.graph.algorithms.maxflow;

import igorilin13.com.github.main.graph.Graph;
import igorilin13.com.github.main.graph.algorithms.BaseGraphAlgorithm;
import igorilin13.com.github.main.graph.algorithms.shortest.DijkstraShortestPath;
import igorilin13.com.github.main.util.ArrayUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EdmondsKarpMaxFlow<T> extends BaseGraphAlgorithm<T> {
    private final int[][] resultFlow;
    private final T sourceName;
    private final T destName;

    public EdmondsKarpMaxFlow(Graph<T> graph, T sourceName, T destName) {
        super(graph, false);
        this.sourceName = sourceName;
        this.destName = destName;
        Map<Graph.Edge<T>, EdgeInfo> flow = run();
        resultFlow = toMatrix(flow);
    }

    private Map<Graph.Edge<T>, EdgeInfo> run() {
        Map<Graph.Edge<T>, EdgeInfo> infos = new HashMap<>();
        for (Graph.Edge<T> edge : graph.getEdges()) {
            EdgeInfo info = new EdgeInfo(edge.getWeight());
            infos.put(edge, info);
        }

        do {
            Graph<T> residualGraph = createResidualGraph(graph, infos);
            List<Graph.Edge<T>> augmentingPath = new DijkstraShortestPath<>(residualGraph, sourceName).getPath(destName);
            if (augmentingPath == null) {
                return infos;
            }
            int minIncrease = Collections.min(augmentingPath, edgeComparatorByWeight).getWeight();
            for (Graph.Edge<T> edge : augmentingPath) {
                EdgeWrapper<T> wrapper = (EdgeWrapper<T>) edge;
                if (wrapper.fakeEdge) {
                    wrapper.edgeInfo.flow -= minIncrease;
                } else {
                    wrapper.edgeInfo.flow += minIncrease;
                }
            }
        } while (true);
    }

    private Graph<T> createResidualGraph(Graph<T> graph, Map<Graph.Edge<T>, EdgeInfo> infos) {
        Graph.GraphBuilder<T> builder = new Graph.GraphBuilder<T>().addVertices(graph.getVerticesByName().keySet());

        for (Graph.Edge<T> edge : graph.getEdges()) {
            EdgeInfo info = infos.get(edge);
            int edgeFlow = info.flow;
            int newCapacity = info.initialCapacity - edgeFlow;

            if (newCapacity > 0) {
                EdgeWrapper<T> reweightedWrapper = new EdgeWrapper<>(edge.getFrom(), edge.getTo(), newCapacity, info, false);
                builder.addEdge(reweightedWrapper);
            }

            if (edgeFlow != 0) {
                Graph.Edge<T> reverseEdge = new EdgeWrapper<>(edge.getTo(), edge.getFrom(), edgeFlow, info, true);
                builder.addEdge(reverseEdge);
            }
        }

        return builder.build();
    }

    private int[][] toMatrix(Map<Graph.Edge<T>, EdgeInfo> flow) {
        int size = graph.size();
        int[][] res = new int[size][size];
        for (Map.Entry<Graph.Edge<T>, EdgeInfo> entry : flow.entrySet()) {
            int fromId = entry.getKey().getFrom().getId();
            int toId = entry.getKey().getTo().getId();
            res[fromId][toId] = entry.getValue().flow;
        }
        return res;
    }

    public int[][] getResultFlow() {
        return ArrayUtils.copy(resultFlow);
    }

    private static class EdgeInfo {
        private final int initialCapacity;
        private int flow;

        private EdgeInfo(int initialCapacity) {
            this.initialCapacity = initialCapacity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            EdgeInfo edgeInfo = (EdgeInfo) o;

            return initialCapacity == edgeInfo.initialCapacity && flow == edgeInfo.flow;
        }

        @Override
        public int hashCode() {
            return initialCapacity;
        }
    }

    private static class EdgeWrapper<T> extends Graph.Edge<T> {
        private final EdgeInfo edgeInfo;
        private final boolean fakeEdge;

        private EdgeWrapper(Graph.Vertex<T> from, Graph.Vertex<T> to, int weight, EdgeInfo edgeInfo, boolean fakeEdge) {
            super(from, to, weight);
            this.edgeInfo = edgeInfo;
            this.fakeEdge = fakeEdge;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            if (!super.equals(o)) {
                return false;
            }

            EdgeWrapper<?> wrapper = (EdgeWrapper<?>) o;

            return fakeEdge == wrapper.fakeEdge && edgeInfo.equals(wrapper.edgeInfo);
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + edgeInfo.hashCode();
            result = 31 * result + (fakeEdge ? 1 : 0);
            return result;
        }
    }
}
