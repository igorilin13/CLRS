package igorilin13.com.github.test.graph.algorithms.shortest;

import igorilin13.com.github.main.graph.algorithms.shortest.DijkstraShortestPath;
import org.junit.Test;

public class DijkstraShortestPathTest extends BaseShortestPathTest {
    private static final int SOURCE = 0;
    private static final int[][] ADJ_MATRIX = {
            {0, 5, 10, 0, 0},
            {0, 0, 3, 9, 2},
            {0, 2, 0, 1, 0},
            {0, 0, 0, 0, 4},
            {7, 0, 0, 6, 0}
    };

    private static final String[] EXPECTED_PATHS = {"0", "01", "012", "0123", "014"};
    private static final int[] EXPECTED_WEIGHTS = {0, 5, 8, 9, 7};

    @Override
    protected int[][] getAdjMatrix() {
        return ADJ_MATRIX;
    }

    @Test
    public void testDijkstra() {
        verifyPaths(EXPECTED_PATHS, EXPECTED_WEIGHTS, new DijkstraShortestPath<>(graph, SOURCE));
    }
}
