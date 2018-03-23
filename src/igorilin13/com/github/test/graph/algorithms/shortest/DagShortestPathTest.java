package igorilin13.com.github.test.graph.algorithms.shortest;

import igorilin13.com.github.main.graph.algorithms.shortest.DAGShortestPath;
import org.junit.Test;

public class DagShortestPathTest extends BaseShortestPathTest {
    private static final int SOURCE = 1;
    private static final int[][] ADJ_MATRIX = {
            {0, 5, 3, 0, 0, 0},
            {0, 0, 2, 6, 0, 0},
            {0, 0, 0, 7, 4, 2},
            {0, 0, 0, 0, -1, 1},
            {0, 0, 0, 0, 0, -2},
            {0, 0, 0, 0, 0, 0}
    };

    private static final String[] EXPECTED_PATHS = {"", "1", "12", "13", "134", "1345"};
    private static final int[] EXPECTED_WEIGHTS = {Integer.MAX_VALUE, 0, 2, 6, 5, 3};

    @Override
    protected int[][] getAdjMatrix() {
        return ADJ_MATRIX;
    }

    @Test
    public void testDag() {
        verifyPaths(EXPECTED_PATHS, EXPECTED_WEIGHTS, new DAGShortestPath<>(graph, SOURCE));
    }
}
