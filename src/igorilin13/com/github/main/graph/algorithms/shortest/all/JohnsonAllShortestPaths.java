package igorilin13.com.github.main.graph.algorithms.shortest.all;

import igorilin13.com.github.main.graph.Graph;
import igorilin13.com.github.main.graph.algorithms.BaseGraphAlgorithm;
import igorilin13.com.github.main.graph.algorithms.shortest.BellmanFordShortestPath;
import igorilin13.com.github.main.graph.algorithms.shortest.DijkstraShortestPath;
import igorilin13.com.github.main.util.ArrayUtils;

import java.util.HashMap;
import java.util.Map;

public class JohnsonAllShortestPaths<T> extends BaseGraphAlgorithm<T> {
    private final int[][] result;

    public JohnsonAllShortestPaths(Graph<T> graph) {
        super(graph, false);
        result = run();
    }

    private int[][] run() {
        Graph.GraphBuilder<T> builder = new Graph.GraphBuilder<>();
        Graph<T> copyWithPlaceholder = builder.copy(graph)
                .addPlaceholderVertex()
                .build();

        BellmanFordShortestPath<T> bellmanShortestPath = new BellmanFordShortestPath<>(copyWithPlaceholder,
                builder.getPlaceholderVertex().getName());
        if (!bellmanShortestPath.solutionExists()) {
            throw new IllegalArgumentException("Solution doesn't exist");
        }

        Map<Graph.Vertex<T>, Integer> offset = new HashMap<>();
        for (Graph.Vertex<T> vertex : graph.getVerticesByName().values()) {
            offset.put(vertex, bellmanShortestPath.getPathWeight(vertex.getName()));
        }

        builder = new Graph.GraphBuilder<T>().addVertices(graph.getVerticesByName().keySet());
        for (Graph.Edge<T> edge : graph.getEdges()) {
            int newWeight = edge.getWeight() + offset.get(edge.getFrom()) - offset.get(edge.getTo());
            builder.addEdge(edge.getFrom().getName(), edge.getTo().getName(), newWeight);
        }
        Graph<T> reweighted = builder.build();

        int size = reweighted.size();
        int[][] result = new int[size][size];
        for (Graph.Vertex<T> from : reweighted.getVerticesByName().values()) {
            DijkstraShortestPath<T> dijkstraShortestPath = new DijkstraShortestPath<>(reweighted, from.getName());
            for (Graph.Vertex<T> to : reweighted.getVerticesByName().values()) {
                result[from.getId()][to.getId()] = dijkstraShortestPath.getPathWeight(to.getName())
                        + offset.get(to) - offset.get(from);
            }
        }
        return result;
    }

    public int[][] getResult() {
        return ArrayUtils.copy(result);
    }
}
