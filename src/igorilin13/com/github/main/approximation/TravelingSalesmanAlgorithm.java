package igorilin13.com.github.main.approximation;

import igorilin13.com.github.main.graph.Graph;
import igorilin13.com.github.main.graph.algorithms.BaseGraphAlgorithm;
import igorilin13.com.github.main.graph.algorithms.elementary.DepthFirstSearch;
import igorilin13.com.github.main.graph.algorithms.spanning.PrimMinSpanningTree;

import java.util.List;

public class TravelingSalesmanAlgorithm<T> extends BaseGraphAlgorithm<T> {
    private final List<Graph.Vertex<T>> hamiltonianCycle;
    private final int pathWeight;

    public TravelingSalesmanAlgorithm(Graph<T> graph) {
        super(graph, false);

        hamiltonianCycle = findHamiltonianCycle();
        pathWeight = calcPathWeight();
    }

    private List<Graph.Vertex<T>> findHamiltonianCycle() {
        Graph<T> minSpanningTreeGraph = new Graph.GraphBuilder<T>()
                .addVertices(graph.getVerticesByName().keySet())
                .addEdges(new PrimMinSpanningTree<>(graph).getResult())
                .build();
        return new DepthFirstSearch<>(minSpanningTreeGraph).getPreordering();
    }

    private int calcPathWeight() {
        int res = 0;
        for (int i = 1; i < hamiltonianCycle.size(); i++) {
            res += findEdgeWeight(i - 1, i);
        }
        res += findEdgeWeight(hamiltonianCycle.size() - 1, 0);
        return res;
    }

    private int findEdgeWeight(int fromIndex, int toIndex) {
        Graph.Vertex<T> from = hamiltonianCycle.get(fromIndex);
        Graph.Vertex<T> to = hamiltonianCycle.get(toIndex);
        return graph.getVerticesByName().get(from.getName()).getEdgeWeight(to);
    }

    public List<Graph.Vertex<T>> getHamiltonianCycle() {
        return hamiltonianCycle;
    }

    public int getPathWeight() {
        return pathWeight;
    }
}
