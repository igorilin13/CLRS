package igorilin13.com.github.test.matrix;

import igorilin13.com.github.main.matrix.LinearEquations;
import igorilin13.com.github.main.util.TupleN;
import igorilin13.com.github.test.Assert;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class LinearEquationsTest {
    private static final double PRECISION = 0.00001;

    @Test
    public void testLuDecomposition() {
        double[][] A = {
                {2, 3, 1, 5},
                {6, 13, 5, 19},
                {2, 19, 10, 23},
                {4, 10, 11, 31}
        };
        double[][] expectedLower = {
                {1, 0, 0, 0},
                {3, 1, 0, 0},
                {1, 4, 1, 0},
                {2, 1, 7, 1}
        };
        double[][] expectedUpper = {
                {2, 3, 1, 5},
                {0, 4, 2, 4},
                {0, 0, 1, 2},
                {0, 0, 0, 3}
        };
        TupleN<double[][]> res = LinearEquations.luDecompose(A);
        Assert.assertEquals(expectedLower, res.get(0), PRECISION);
        Assert.assertEquals(expectedUpper, res.get(1), PRECISION);
    }

    @Test
    public void testSolve() {
        double[][] A = {
                {2, 5, 4, 1},
                {1, 3, 2, 1},
                {2, 10, 9, 9},
                {3, 8, 9, 2}
        };
        double[] b = {20, 11, 40, 37};
        double[] expectedRes = {1, 2, 2, 0};
        assertArrayEquals(expectedRes, LinearEquations.solve(A, b), PRECISION);
    }
}
