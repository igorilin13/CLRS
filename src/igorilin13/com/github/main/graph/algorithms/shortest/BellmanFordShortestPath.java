package igorilin13.com.github.main.graph.algorithms.shortest;

import igorilin13.com.github.main.graph.Graph;

public class BellmanFordShortestPath<T> extends BaseShortestPathAlgorithm<T> {
    private boolean containsNegativeCycle;

    public BellmanFordShortestPath(Graph<T> graph, T source) {
        super(graph, source);
        run();
    }

    private void run() {
        forEachEdge(this::relax);
        forEachEdge((from, to, edge) -> {
            if (to.getPathWeight() > from.getPathWeight() + edge.getWeight()) {
                containsNegativeCycle = true;
            }
        });
    }

    public boolean solutionExists() {
        return !containsNegativeCycle;
    }
}
