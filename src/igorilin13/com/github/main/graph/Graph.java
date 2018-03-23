package igorilin13.com.github.main.graph;

import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

public class Graph<T> {
    private final Map<T, Vertex<T>> verticesByName = new HashMap<>();
    private final Set<Edge<T>> edges = new HashSet<>();

    private Graph() {
    }

    public Map<T, Vertex<T>> getVerticesByName() {
        return verticesByName;
    }

    public Set<Edge<T>> getEdges() {
        return edges;
    }

    public int size() {
        return verticesByName.size();
    }

    public int[][] toMatrix() {
        int size = size();
        int[][] res = new int[size][size];
        for (Vertex<T> vertex : verticesByName.values()) {
            vertex.forEachAdj(edge -> res[edge.getFrom().id][edge.getTo().id] = edge.getWeight());
        }
        return res;
    }

    public static class GraphBuilder<T> {
        private final Graph<T> graph = new Graph<>();
        private final Vertex<T> placeholderVertex = new Vertex<>(null, -1);
        private int nextVertexId = 0;

        public GraphBuilder<T> addVertices(Collection<T> names) {
            for (T name : new HashSet<>(names)) {
                addVertex(name);
            }
            return this;
        }

        public GraphBuilder<T> addVertex(T name) {
            graph.verticesByName.put(name, new Vertex<>(name, nextVertexId));
            nextVertexId++;
            return this;
        }

        public GraphBuilder<T> addPlaceholderVertex() {
            graph.verticesByName.put(null, placeholderVertex);
            for (T to : graph.getVerticesByName().keySet()) {
                addEdge(placeholderVertex.getName(), to, 0);
            }
            return this;
        }

        public GraphBuilder<T> addEdges(Collection<Edge<T>> edges) {
            for (Edge<T> edge : edges) {
                addEdge(edge);
            }
            return this;
        }

        public GraphBuilder<T> addEdge(T fromName, T toName, int weight) {
            Vertex<T> from = graph.verticesByName.get(fromName);
            Vertex<T> to = graph.verticesByName.get(toName);
            if (from == null || to == null) {
                throw new IllegalArgumentException("Edge contains unknown vertex");
            }
            Edge<T> edge = new Edge<>(from, to, weight);
            from.adj.put(to, edge);
            graph.edges.add(edge);
            return this;
        }

        public GraphBuilder<T> addEdge(Edge<T> edge) {
            Vertex<T> from = graph.verticesByName.get(edge.getFrom().getName());
            Vertex<T> to = graph.verticesByName.get(edge.getTo().getName());
            if (from == null || to == null) {
                throw new IllegalArgumentException("Edge contains unknown vertex");
            }
            from.adj.put(to, edge);
            graph.edges.add(edge);
            return this;
        }

        public GraphBuilder<T> copy(Graph<T> graph) {
            addVertices(graph.getVerticesByName().keySet());
            for (Graph.Edge<T> edge : graph.getEdges()) {
                addEdge(edge);
            }
            return this;
        }

        public Vertex<T> getPlaceholderVertex() {
            return placeholderVertex;
        }

        public Graph<T> build() {
            return graph;
        }

        public static Graph<Integer> fromMatrix(int[][] adjMatrix) {
            GraphBuilder<Integer> builder = new GraphBuilder<>();

            Set<Integer> vertices = new LinkedHashSet<>();
            for (int i = 0; i < adjMatrix.length; i++) {
                vertices.add(i);
            }
            builder.addVertices(vertices);

            for (int i = 0; i < adjMatrix.length; i++) {
                for (int j = 0; j < adjMatrix.length; j++) {
                    if (adjMatrix[i][j] != 0) {
                        builder.addEdge(i, j, adjMatrix[i][j]);
                    }
                }
            }

            return builder.build();
        }
    }

    public static final class Vertex<T> {
        @Nullable
        private final T name;
        private final int id;
        private final Map<Vertex<T>, Edge<T>> adj = new HashMap<>();

        private Vertex(@Nullable T uniqueName, int id) {
            this.name = uniqueName;
            this.id = id;
        }

        public void forEachAdj(Consumer<Edge<T>> action) {
            for (Edge<T> edge : adj.values()) {
                action.accept(edge);
            }
        }

        public int getEdgeWeight(Vertex to) {
            return adj.get(to).getWeight();
        }

        @Nullable
        public T getName() {
            return name;
        }

        public Map<Vertex<T>, Edge<T>> getAdj() {
            return adj;
        }

        public int getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Vertex<?> vertex = (Vertex<?>) o;

            return id == vertex.id && (name != null ? name.equals(vertex.name) : vertex.name == null);
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + id;
            return result;
        }

        @Override
        public String toString() {
            return name != null ? name.toString() : "";
        }
    }

    public static class Edge<T> {
        private final Vertex<T> from;
        private final Vertex<T> to;
        private final int weight;

        public Edge(Vertex<T> from, Vertex<T> to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public Vertex<T> getFrom() {
            return from;
        }

        public Vertex<T> getTo() {
            return to;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Edge<?> edge = (Edge<?>) o;

            return weight == edge.weight && from.equals(edge.from) && to.equals(edge.to);
        }

        @Override
        public int hashCode() {
            int result = from.hashCode();
            result = 31 * result + to.hashCode();
            result = 31 * result + weight;
            return result;
        }

        @Override
        public String toString() {
            return from.name + " -> " + to.name + ": " + weight;
        }
    }
}
