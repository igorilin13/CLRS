package igorilin13.com.github.main.graph.algorithms.shortest;

import igorilin13.com.github.main.graph.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DijkstraShortestPath<T> extends BaseShortestPathAlgorithm<T> {
    public DijkstraShortestPath(Graph<T> graph, T source) {
        super(graph, source);
        run();
    }

    private void run() {
        List<VertexWrapper> queue = new ArrayList<>();
        for (BaseVertexWrapper vertexWrapper : vertexWrappers.values()) {
            queue.add((VertexWrapper) vertexWrapper);
        }
        while (!queue.isEmpty()) {
            queue.sort(Comparator.comparingInt(VertexWrapper::getPathWeight));
            Graph.Vertex<T> current = queue.remove(0).getVertex();
            current.forEachAdj(this::relax);
        }
    }
}
