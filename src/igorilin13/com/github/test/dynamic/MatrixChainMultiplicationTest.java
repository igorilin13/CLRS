package igorilin13.com.github.test.dynamic;

import igorilin13.com.github.main.dynamic.MatrixChainMultiplication;
import org.junit.Test;

import static igorilin13.com.github.main.dynamic.MatrixChainMultiplication.MATRIX_NAME;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MatrixChainMultiplicationTest {
    private static final int[] TEST_DIMENSIONS = {30, 35, 15, 5, 10, 20, 25};
    private static final int[][] EXPECTED_MINIMUM_COST = {
            {0, 15750, 7875, 9375, 11875, 15125},
            {0, 0, 2625, 4375, 7125, 10500},
            {0, 0, 0, 750, 2500, 5375},
            {0, 0, 0, 0, 1000, 3500},
            {0, 0, 0, 0, 0, 5000},
            {0, 0, 0, 0, 0, 0}
    };
    private static final String EXPECTED_OPTIMAL_PARENS = "((" + MATRIX_NAME + "(" +
            MATRIX_NAME + MATRIX_NAME + "))((" + MATRIX_NAME + MATRIX_NAME + ")" + MATRIX_NAME + "))";

    @Test
    public void testMinimumCost() {
        assertArrayEquals(EXPECTED_MINIMUM_COST, MatrixChainMultiplication.matrixChainOrder(TEST_DIMENSIONS).get(0));
    }

    @Test
    public void testOptimalParens() {
        assertEquals(EXPECTED_OPTIMAL_PARENS, MatrixChainMultiplication.getOptimalParens(TEST_DIMENSIONS));
    }
}
