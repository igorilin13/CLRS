package igorilin13.com.github.test.graph.algorithms.shortest;

import igorilin13.com.github.main.graph.algorithms.shortest.BellmanFordShortestPath;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BellmanFordShortestPathTest extends BaseShortestPathTest {
    private static final int SOURCE = 0;
    private static final int[][] ADJ_MATRIX = {
            {0, 6, 0, 7, 0},
            {0, 0, 5, 8, -4},
            {0, -2, 0, 0, 0},
            {0, 0, -3, 0, 9},
            {2, 0, 7, 0, 0},
    };

    private static final String[] EXPECTED_PATHS = {"0", "0321", "032", "03", "03214"};
    private static final int[] EXPECTED_WEIGHTS = {0, 2, 4, 7, -2};

    @Override
    protected int[][] getAdjMatrix() {
        return ADJ_MATRIX;
    }

    @Test
    public void testBellmanFord() {
        BellmanFordShortestPath<Integer> result = new BellmanFordShortestPath<>(graph, SOURCE);
        assertTrue(result.solutionExists());
        verifyPaths(EXPECTED_PATHS, EXPECTED_WEIGHTS, result);
    }
}
