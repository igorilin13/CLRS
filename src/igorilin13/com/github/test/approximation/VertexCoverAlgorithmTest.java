package igorilin13.com.github.test.approximation;

import igorilin13.com.github.main.approximation.VertexCoverAlgorithm;
import igorilin13.com.github.main.graph.Graph;
import igorilin13.com.github.test.GraphAssert;
import igorilin13.com.github.test.graph.algorithms.BaseGraphTest;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertTrue;

public class VertexCoverAlgorithmTest extends BaseGraphTest {
    private static final int[][] ADJ_MATRIX = {
            {0, 1, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 0, 0, 0},
            {0, 1, 0, 1, 1, 0, 0},
            {0, 0, 1, 0, 1, 1, 1},
            {0, 0, 1, 1, 0, 1, 0},
            {0, 0, 0, 1, 1, 0, 0},
            {0, 0, 0, 1, 0, 0, 0}

    };

    private static final int OPTIMAL_VERTEX_COVER_SIZE = 3;
    private static final int APPROXIMATION_COMPLEXITY = 2;

    @Test
    public void testVertexCover() {
        Set<Graph.Vertex<Integer>> result = new VertexCoverAlgorithm<>(graph).getVertexCover();
        GraphAssert.assertVertexCover(graph, result);
        assertTrue(result.size() <= APPROXIMATION_COMPLEXITY * OPTIMAL_VERTEX_COVER_SIZE);
    }

    @Override
    protected int[][] getAdjMatrix() {
        return ADJ_MATRIX;
    }
}
