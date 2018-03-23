package igorilin13.com.github.test;

import igorilin13.com.github.main.graph.Graph;

import java.util.Set;

import static org.junit.Assert.assertTrue;

public class GraphAssert {
    public static <T> void assertVertexCover(Graph<T> graph, Set<Graph.Vertex<T>> vertexCover) {
        for (Graph.Edge<T> edge : graph.getEdges()) {
            assertTrue(vertexCover.contains(edge.getFrom()) || vertexCover.contains(edge.getTo()));
        }
    }
}
