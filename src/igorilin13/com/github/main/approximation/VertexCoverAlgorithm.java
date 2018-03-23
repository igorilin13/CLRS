package igorilin13.com.github.main.approximation;

import igorilin13.com.github.main.graph.Graph;
import igorilin13.com.github.main.graph.algorithms.BaseGraphAlgorithm;

import java.util.*;
import java.util.function.Predicate;

public class VertexCoverAlgorithm<T> extends BaseGraphAlgorithm<T> {
    private final Set<Graph.Vertex<T>> vertexCover;

    public VertexCoverAlgorithm(Graph<T> graph) {
        super(graph, false);
        vertexCover = run();
    }

    private Set<Graph.Vertex<T>> run() {
        Set<Graph.Vertex<T>> result = new HashSet<>();
        List<Graph.Edge<T>> edges = new ArrayList<>(graph.getEdges());
        Random random = new Random();
        while (!edges.isEmpty()) {
            Graph.Edge<T> next = edges.get(random.nextInt(edges.size()));
            result.add(next.getFrom());
            result.add(next.getTo());
            edges.removeIf(new EdgeIncidentPredicate(next));
        }
        return result;
    }

    public Set<Graph.Vertex<T>> getVertexCover() {
        return vertexCover;
    }

    private class EdgeIncidentPredicate implements Predicate<Graph.Edge<T>> {
        private final Graph.Vertex<T> first;
        private final Graph.Vertex<T> second;

        private EdgeIncidentPredicate(Graph.Edge<T> edge) {
            first = edge.getFrom();
            second = edge.getTo();
        }

        @Override
        public boolean test(Graph.Edge<T> edge) {
            Graph.Vertex<T> from = edge.getFrom();
            Graph.Vertex<T> to = edge.getTo();
            return from == first || from == second
                    || to == first || to == second;
        }
    }
}
