package igorilin13.com.github.test.linear;

import igorilin13.com.github.main.linear.SimplexAlgorithm;
import igorilin13.com.github.main.linear.SimplexSlackForm;
import igorilin13.com.github.test.Assert;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SimplexAlgorithmTest {
    private static final double PRECISION = 0.00001;

    @Test
    public void testSimplex() {
        double[][] A = {
                {1, 2, 3},
                {2, 2, 5},
                {4, 1, 2}
        };
        double[] b = {30, 24, 36};
        double[] c = {3, 1, 2};

        double[] expectedSolution = {8, 4, 0};

        assertArrayEquals(expectedSolution, new SimplexAlgorithm(A, b, c).solve(), PRECISION);
    }

    @Test
    public void testInitSimplex() {
        double[][] A = {
                {2, -1},
                {1, -5}
        };
        double[] b = {2, -4};
        double[] c = {2, -1};

        SimplexSlackForm result = new SimplexAlgorithm(A, b, c).initSimplex();

        Assert.assertArrayHasSameValues(new int[]{1, 2}, result.getBasic());
        Assert.assertArrayHasSameValues(new int[]{0, 3}, result.getNonBasic());
        Assert.assertEquals(new double[][]{
                {0, 0, 0, 0},
                {-0.2, 0, 0, -0.2},
                {1.8, 0, 0, -0.2},
                {0, 0, 0, 0}
        }, result.getA(), 0.1);
        assertArrayEquals(new double[]{0, 0.8, 2.8, 0}, result.getB(), PRECISION);
        assertEquals(-0.8, result.getV(), 0.1);
        assertArrayEquals(new double[]{1.8, 0, 0, -0.2}, result.getC(), PRECISION);
    }
}
