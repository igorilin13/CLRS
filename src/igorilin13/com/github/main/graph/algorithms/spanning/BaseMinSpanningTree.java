package igorilin13.com.github.main.graph.algorithms.spanning;

import igorilin13.com.github.main.graph.Graph;
import igorilin13.com.github.main.graph.algorithms.BaseGraphAlgorithm;

import java.util.HashSet;
import java.util.Set;

public abstract class BaseMinSpanningTree<T> extends BaseGraphAlgorithm<T> {
    protected final Set<Graph.Edge<T>> result;

    BaseMinSpanningTree(Graph<T> graph, boolean needsWrappers) {
        super(graph, needsWrappers);
        result = new HashSet<>();
    }

    public Set<Graph.Edge<T>> getResult() {
        return result;
    }
}
