package igorilin13.com.github.main.graph.algorithms.shortest;

import igorilin13.com.github.main.graph.Graph;
import igorilin13.com.github.main.graph.algorithms.elementary.TopologicalSort;

import java.util.List;

public class DAGShortestPath<T> extends BaseShortestPathAlgorithm<T> {
    public DAGShortestPath(Graph<T> graph, T source) {
        super(graph, source);
        run();
    }

    private void run() {
        List<Graph.Vertex<T>> topologicallySorted = new TopologicalSort<>(graph).getResult();
        for (Graph.Vertex<T> vertex : topologicallySorted) {
            vertex.forEachAdj(this::relax);
        }
    }
}
