package igorilin13.com.github.main.graph.algorithms.spanning;

import igorilin13.com.github.main.graph.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PrimMinSpanningTree<T> extends BaseMinSpanningTree<T> {
    public PrimMinSpanningTree(Graph<T> graph) {
        super(graph, true);
        run();
    }

    @Override
    protected BaseVertexWrapper createWrapper(Graph.Vertex<T> vertex) {
        return new VertexWrapper(vertex);
    }

    private void run() {
        VertexWrapper root = (VertexWrapper) vertexWrappers.values().iterator().next();
        root.key = 0;
        List<VertexWrapper> queue = new ArrayList<>();
        for (BaseVertexWrapper baseWrapper : vertexWrappers.values()) {
            queue.add((VertexWrapper) baseWrapper);
        }
        while (!queue.isEmpty()) {
            queue.sort(Comparator.comparingInt(vertexWrapper -> vertexWrapper.key));
            VertexWrapper current = queue.remove(0);
            for (BaseVertexWrapper adjBaseWrapper : current.getAdjWrappers()) {
                VertexWrapper adjWrapper = (VertexWrapper) adjBaseWrapper;
                if (queue.contains(adjWrapper) && adjWrapper.key > current.getEdgeWeight(adjBaseWrapper)) {
                    adjWrapper.parent = current;
                    adjWrapper.key = current.getEdgeWeight(adjBaseWrapper);
                }
            }
        }
        setResult();
    }

    private void setResult() {
        for (BaseVertexWrapper baseWrapper : vertexWrappers.values()) {
            VertexWrapper vertex = (VertexWrapper) baseWrapper;
            if (vertex.parent != null) {
                result.add(new Graph.Edge<>(vertex.parent.getVertex(), vertex.getVertex(),
                        vertex.parent.getEdgeWeight(baseWrapper)));
            }
        }
    }

    private class VertexWrapper extends BaseVertexWrapper {
        private int key;
        private VertexWrapper parent;

        VertexWrapper(Graph.Vertex<T> vertex) {
            super(vertex);
            key = POS_INF;
        }
    }
}
