package igorilin13.com.github.main.graph.algorithms.shortest;

import igorilin13.com.github.main.graph.Graph;
import igorilin13.com.github.main.graph.algorithms.PathAlgorithm;
import igorilin13.com.github.main.util.Action3;

public abstract class BaseShortestPathAlgorithm<T> extends PathAlgorithm<T> {

    BaseShortestPathAlgorithm(Graph<T> graph, T sourceName) {
        super(graph, true, sourceName);
        VertexWrapper source = (VertexWrapper) vertexWrappers.get(sourceName);
        source.pathWeight = 0;
    }

    void relax(Graph.Edge<T> edge) {
        relax((VertexWrapper) vertexWrappers.get(edge.getFrom().getName()),
                (VertexWrapper) vertexWrappers.get(edge.getTo().getName()),
                edge);
    }

    void relax(VertexWrapper from, VertexWrapper to, Graph.Edge<T> edge) {
        if (from.pathWeight != POS_INF && to.pathWeight > from.pathWeight + edge.getWeight()) {
            to.pathWeight = from.pathWeight + edge.getWeight();
            to.parent = from;
            to.edge = edge;
        }
    }

    void forEachEdge(Action3<VertexWrapper, VertexWrapper, Graph.Edge<T>> action) {
        for (int i = 0; i < vertexWrappers.size(); i++) {
            for (Graph.Edge<T> edge : graph.getEdges()) {
                VertexWrapper from = (VertexWrapper) vertexWrappers.get(edge.getFrom().getName());
                VertexWrapper to = (VertexWrapper) vertexWrappers.get(edge.getTo().getName());
                action.run(from, to, edge);
            }
        }
    }

    @Override
    protected BaseVertexWrapper createWrapper(Graph.Vertex<T> vertex) {
        return new VertexWrapper(vertex);
    }

    @Override
    protected PathVertexWrapper<T> findVertexWrapper(T name) {
        return (VertexWrapper) vertexWrappers.get(name);
    }

    class VertexWrapper extends BaseVertexWrapper implements PathAlgorithm.PathVertexWrapper<T> {
        private VertexWrapper parent;
        private int pathWeight;
        private Graph.Edge<T> edge;

        private VertexWrapper(Graph.Vertex<T> vertex) {
            super(vertex);
            pathWeight = POS_INF;
        }

        @Override
        public int getPathWeight() {
            return pathWeight;
        }

        @Override
        public PathAlgorithm.PathVertexWrapper<T> getParent() {
            return parent;
        }

        @Override
        public Graph.Edge<T> getEdge() {
            return edge;
        }

        @Override
        public String toString() {
            return getName().toString();
        }
    }
}
