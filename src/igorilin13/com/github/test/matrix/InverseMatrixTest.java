package igorilin13.com.github.test.matrix;

import igorilin13.com.github.main.matrix.InverseMatrix;
import igorilin13.com.github.test.Assert;
import org.junit.Test;

public class InverseMatrixTest {
    private static final double PRECISION = 0.1;

    private static final double[][] INPUT = {
            {1, 0, 2},
            {2, -1, 1},
            {1, 3, -1}
    };

    private static final double[][] EXPECTED_INVERSE = {
            {-0.17, 0.50, 0.17},
            {0.25, -0.25, 0.25},
            {0.58, -0.25, -0.08}
    };

    @Test
    public void testInverseMatrix() {
        Assert.assertEquals(EXPECTED_INVERSE, InverseMatrix.compute(INPUT), PRECISION);
    }
}
