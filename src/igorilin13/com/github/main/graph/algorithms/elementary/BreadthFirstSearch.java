package igorilin13.com.github.main.graph.algorithms.elementary;

import igorilin13.com.github.main.graph.Graph;
import igorilin13.com.github.main.graph.Graph.Vertex;
import igorilin13.com.github.main.graph.algorithms.PathAlgorithm;

import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch<T> extends PathAlgorithm<T> {
    private final VertexWrapper source;

    public BreadthFirstSearch(Graph<T> graph, T sourceName) {
        super(graph, true, sourceName);
        source = (VertexWrapper) vertexWrappers.get(sourceName);
        if (source == null) {
            throw new IllegalArgumentException("Source vertex not found");
        }
        run();
    }

    @Override
    protected BaseVertexWrapper createWrapper(Vertex<T> vertex) {
        return new VertexWrapper(vertex);
    }

    private void run() {
        source.color = Color.GRAY;
        source.pathWeight = 0;

        Queue<VertexWrapper> queue = new LinkedList<>();
        queue.add(source);
        while (!queue.isEmpty()) {
            VertexWrapper current = queue.remove();
            for (BaseVertexWrapper adjWrapper : current.getAdjWrappers()) {
                VertexWrapper adj = (VertexWrapper) adjWrapper;
                if (adj.color == Color.WHITE) {
                    adj.color = Color.GRAY;
                    adj.pathWeight = current.pathWeight + 1;
                    adj.parent = current;
                    adj.edge = new Graph.Edge<>(current.getVertex(), adj.getVertex(), 1);
                    queue.add(adj);
                }
            }
            current.color = Color.BLACK;
        }
    }

    @Override
    protected PathVertexWrapper<T> findVertexWrapper(T name) {
        return (VertexWrapper) vertexWrappers.get(name);
    }

    private class VertexWrapper extends BaseVertexWrapper implements PathVertexWrapper<T> {
        private Color color;
        private int pathWeight;
        private VertexWrapper parent;
        private Graph.Edge<T> edge;

        private VertexWrapper(Vertex<T> vertex) {
            super(vertex);
            color = Color.WHITE;
            pathWeight = POS_INF;
        }

        @Override
        public PathVertexWrapper<T> getParent() {
            return parent;
        }

        @Override
        public int getPathWeight() {
            return pathWeight;
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
