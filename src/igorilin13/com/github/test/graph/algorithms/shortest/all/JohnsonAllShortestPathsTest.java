package igorilin13.com.github.test.graph.algorithms.shortest.all;

import igorilin13.com.github.main.graph.algorithms.shortest.all.JohnsonAllShortestPaths;
import igorilin13.com.github.test.graph.algorithms.BaseGraphTest;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class JohnsonAllShortestPathsTest extends BaseGraphTest {
    private static final int[][] ADJ_MATRIX = {
            {0, 3, 8, 0, -4},
            {0, 0, 0, 1, 7},
            {0, 4, 0, 0, 0},
            {2, 0, -5, 0, 0},
            {0, 0, 0, 6, 0},
    };

    private static final int[][] EXPECTED_RESULT = {
            {0, 1, -3, 2, -4},
            {3, 0, -4, 1, -1},
            {7, 4, 0, 5, 3},
            {2, -1, -5, 0, -2},
            {8, 5, 1, 6, 0}
    };

    @Test
    public void testJohnsonShortestPaths() {
        assertArrayEquals(EXPECTED_RESULT, new JohnsonAllShortestPaths<>(graph).getResult());
    }

    @Override
    protected int[][] getAdjMatrix() {
        return ADJ_MATRIX;
    }
}
