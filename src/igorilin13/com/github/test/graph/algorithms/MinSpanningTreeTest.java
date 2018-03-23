package igorilin13.com.github.test.graph.algorithms;

import igorilin13.com.github.main.graph.Graph;
import igorilin13.com.github.main.graph.algorithms.spanning.KruskalMinSpanningTree;
import igorilin13.com.github.main.graph.algorithms.spanning.PrimMinSpanningTree;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class MinSpanningTreeTest extends BaseGraphTest {
    private static final int EXPECTED_WEIGHT = 37;
    private static final int[][] ADJ_MATRIX = {
            {0, 4, 0, 0, 0, 0, 0, 8, 0},
            {4, 0, 8, 0, 0, 0, 0, 11, 0},
            {0, 8, 0, 7, 0, 4, 0, 0, 2},
            {0, 0, 7, 0, 9, 14, 0, 0, 0},
            {0, 0, 0, 9, 0, 10, 0, 0, 0},
            {0, 0, 4, 14, 10, 0, 2, 0, 0},
            {0, 0, 0, 0, 0, 2, 0, 1, 6},
            {8, 11, 0, 0, 0, 0, 1, 0, 7},
            {0, 0, 2, 0, 0, 0, 6, 7, 0}
    };

    @Override
    protected int[][] getAdjMatrix() {
        return ADJ_MATRIX;
    }

    @Test
    public void testKruskal() {
        verifySpanningTree(new KruskalMinSpanningTree<>(graph).getResult());
    }

    @Test
    public void testPrim() {
        verifySpanningTree(new PrimMinSpanningTree<>(graph).getResult());
    }

    private void verifySpanningTree(Set<Graph.Edge<Integer>> edges) {
        Set<Graph.Vertex<Integer>> unexplored = new HashSet<>(graph.getVerticesByName().values());
        int sum = 0;
        for (Graph.Edge<Integer> edge : edges) {
            unexplored.remove(edge.getFrom());
            unexplored.remove(edge.getTo());
            sum += edge.getWeight();
        }
        assertEquals(0, unexplored.size());
        assertEquals(EXPECTED_WEIGHT, sum);
    }
}
