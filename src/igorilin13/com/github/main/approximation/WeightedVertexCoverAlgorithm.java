package igorilin13.com.github.main.approximation;

import igorilin13.com.github.main.graph.Graph;
import igorilin13.com.github.main.graph.algorithms.BaseGraphAlgorithm;
import igorilin13.com.github.main.linear.SimplexAlgorithm;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class WeightedVertexCoverAlgorithm<T>
        extends BaseGraphAlgorithm<WeightedVertexCoverAlgorithm.WeightedVertex<T>> {
    private final Set<Graph.Vertex<WeightedVertex<T>>> vertexCover;
    private final int vertexCoverWeight;

    public WeightedVertexCoverAlgorithm(Graph<WeightedVertexCoverAlgorithm.WeightedVertex<T>> graph) {
        super(graph, false);
        vertexCover = run();
        vertexCoverWeight = calculateWeight();
    }

    private Set<Graph.Vertex<WeightedVertex<T>>> run() {
        double[] solution = findLinearProgrammingSolution();
        Set<Graph.Vertex<WeightedVertex<T>>> result = new HashSet<>();
        for (Graph.Vertex<WeightedVertex<T>> vertex : graph.getVerticesByName().values()) {
            if (solution[vertex.getId()] > 0.5) {
                result.add(vertex);
            }
        }
        return result;
    }

    private double[] findLinearProgrammingSolution() {
        int totalVertices = graph.size();
        int totalEdges = graph.getEdges().size();

        double[][] A = new double[totalEdges + totalVertices][totalVertices];
        double[] b = new double[totalEdges + totalVertices];
        int row = 0;
        for (Graph.Edge<WeightedVertex<T>> edge : graph.getEdges()) {
            A[row][edge.getFrom().getId()] = -1;
            A[row][edge.getTo().getId()] = -1;
            b[row] = -1;
            row++;
        }

        double[] c = new double[totalVertices];
        for (Graph.Vertex<WeightedVertex<T>> vertex : graph.getVerticesByName().values()) {
            int vertexId = vertex.getId();
            c[vertexId] = -1 * vertex.getName().weight;
            A[row][vertexId] = 1;
            b[row] = 1;
            row++;
        }

        return new SimplexAlgorithm(A, b, c).solve();
    }

    private int calculateWeight() {
        int weight = 0;
        for (Graph.Vertex<WeightedVertex<T>> vertex : vertexCover) {
            weight += vertex.getName().weight;
        }
        return weight;
    }

    public Set<Graph.Vertex<WeightedVertex<T>>> getVertexCover() {
        return vertexCover;
    }

    public int getVertexCoverWeight() {
        return vertexCoverWeight;
    }

    public static class WeightedVertex<T> {
        private final T name;
        private final int weight;

        public WeightedVertex(T name, int weight) {
            this.name = name;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WeightedVertex<?> that = (WeightedVertex<?>) o;
            return weight == that.weight &&
                    Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, weight);
        }

        @Override
        public String toString() {
            return "(" + name + ": " + weight + ")";
        }
    }
}
