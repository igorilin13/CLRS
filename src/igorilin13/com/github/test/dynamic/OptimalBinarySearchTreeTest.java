package igorilin13.com.github.test.dynamic;

import igorilin13.com.github.main.dynamic.OptimalBinarySearchTree;
import igorilin13.com.github.main.util.TupleN;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class OptimalBinarySearchTreeTest {
    private static final int[] TEST_KEY_PROBABILITY = {15, 10, 5, 10, 20};
    private static final int[] TEST_DUMMY_KEY_PROBABILITY = {5, 10, 5, 5, 5, 10};

    private static final int[][] EXPECTED_SEARCH_COST = {
            {5, 45, 90, 125, 175, 275},
            {0, 10, 40, 70, 120, 200},
            {0, 0, 5, 25, 60, 130},
            {0, 0, 0, 5, 30, 90},
            {0, 0, 0, 0, 5, 50},
            {0, 0, 0, 0, 0, 10}
    };
    private static final int[][] EXPECTED_ROOT = {
            {1, 1, 2, 2, 2},
            {0, 2, 2, 2, 4},
            {0, 0, 3, 4, 5},
            {0, 0, 0, 4, 5},
            {0, 0, 0, 0, 5}
    };

    @Test
    public void testOptimalBst() {
        TupleN<int[][]> res = OptimalBinarySearchTree.optimalBst(TEST_KEY_PROBABILITY, TEST_DUMMY_KEY_PROBABILITY);
        assertArrayEquals(EXPECTED_SEARCH_COST, res.get(0));
        assertArrayEquals(EXPECTED_ROOT, res.get(1));
    }
}
