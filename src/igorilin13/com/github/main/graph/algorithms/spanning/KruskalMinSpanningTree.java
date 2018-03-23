package igorilin13.com.github.main.graph.algorithms.spanning;

import igorilin13.com.github.main.datastructures.advanced.DisjointSetForest;
import igorilin13.com.github.main.graph.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KruskalMinSpanningTree<T> extends BaseMinSpanningTree<T> {
    public KruskalMinSpanningTree(Graph<T> graph) {
        super(graph, false);
        run();
    }

    private void run() {
        DisjointSetForest<Graph.Vertex<T>> componentsForest = new DisjointSetForest<>();

        for (Graph.Vertex<T> vertex : graph.getVerticesByName().values()) {
            componentsForest.makeSet(vertex);
        }

        List<Graph.Edge<T>> edges = new ArrayList<>(graph.getEdges());
        edges.sort(Comparator.comparingInt(Graph.Edge::getWeight));

        for (Graph.Edge<T> edge : edges) {
            Graph.Vertex<T> from = edge.getFrom();
            Graph.Vertex<T> to = edge.getTo();
            if (componentsForest.findSet(from) != componentsForest.findSet(to)) {
                result.add(edge);
                componentsForest.union(from, to);
            }
        }
    }
}
