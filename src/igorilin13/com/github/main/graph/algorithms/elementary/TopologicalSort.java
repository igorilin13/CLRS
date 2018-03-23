package igorilin13.com.github.main.graph.algorithms.elementary;

import igorilin13.com.github.main.graph.Graph;
import igorilin13.com.github.main.graph.algorithms.BaseGraphAlgorithm;

import java.util.List;

public class TopologicalSort<T> extends BaseGraphAlgorithm<T> {
    private final List<Graph.Vertex<T>> result;

    public TopologicalSort(Graph<T> graph) {
        super(graph, false);
        result = new DepthFirstSearch<>(graph).getReversePostordering();
    }

    public List<Graph.Vertex<T>> getResult() {
        return result;
    }
}
