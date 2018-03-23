package igorilin13.com.github.main.graph.algorithms;

import igorilin13.com.github.main.graph.Graph;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public abstract class BaseGraphAlgorithm<T> {
    protected static final int POS_INF = Integer.MAX_VALUE;

    protected final Graph<T> graph;
    protected final Map<T, BaseVertexWrapper> vertexWrappers;

    protected final Comparator<Graph.Edge<T>> edgeComparatorByWeight = Comparator.comparingInt(Graph.Edge::getWeight);

    protected BaseGraphAlgorithm(Graph<T> graph, boolean needsWrappers) {
        this.graph = graph;
        vertexWrappers = new HashMap<>();
        if (needsWrappers) {
            for (Graph.Vertex<T> vertex : graph.getVerticesByName().values()) {
                BaseVertexWrapper wrapper = createWrapper(vertex);
                vertexWrappers.put(vertex.getName(), wrapper);
            }
            for (BaseVertexWrapper wrapper : vertexWrappers.values()) {
                wrapper.setAdjWrappers();
            }
        }
    }

    protected BaseVertexWrapper createWrapper(Graph.Vertex<T> vertex) {
        throw new NotImplementedException();
    }

    protected List<Graph.Vertex<T>> toVertexList(Collection<BaseVertexWrapper> wrappers) {
        List<Graph.Vertex<T>> result = new ArrayList<>();
        for (BaseVertexWrapper wrapper : wrappers) {
            result.add(wrapper.getVertex());
        }
        return result;
    }

    protected abstract class BaseVertexWrapper {
        private final Graph.Vertex<T> vertex;
        private final List<BaseVertexWrapper> adjWrappers;

        protected BaseVertexWrapper(Graph.Vertex<T> vertex) {
            this.vertex = vertex;
            this.adjWrappers = new ArrayList<>();
        }

        private void setAdjWrappers() {
            for (Graph.Vertex<T> adjVertex : vertex.getAdj().keySet()) {
                adjWrappers.add(vertexWrappers.get(adjVertex.getName()));
            }
        }

        protected T getName() {
            return vertex.getName();
        }

        public List<BaseVertexWrapper> getAdjWrappers() {
            return adjWrappers;
        }

        public int getEdgeWeight(BaseVertexWrapper to) {
            return vertex.getEdgeWeight(to.vertex);
        }

        public Graph.Vertex<T> getVertex() {
            return vertex;
        }
    }

    protected enum Color {
        WHITE,
        GRAY,
        BLACK
    }
}
