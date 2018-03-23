package igorilin13.com.github.test.parallel;

import igorilin13.com.github.main.parallel.MatrixVectorMultiply;
import igorilin13.com.github.test.RandomValueGenerator;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class MatrixVectorMultiplyTest {
    private static final int SIZE = 10;
    private static final int[][] MATRIX = new int[SIZE][SIZE];
    private static final int[] VECTOR = new int[SIZE];
    private static final int[] EXPECTED_RESULT = new int[SIZE];

    @BeforeClass
    public static void setUpClass() {
        for (int i = 0; i < SIZE; i++) {
            VECTOR[i] = RandomValueGenerator.randomInt();
            for (int j = 0; j < SIZE; j++) {
                MATRIX[i][j] = RandomValueGenerator.randomInt();
            }
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                EXPECTED_RESULT[i] += MATRIX[i][j] * VECTOR[j];
            }
        }
    }

    @Test
    public void testParallel() {
        assertArrayEquals(EXPECTED_RESULT, MatrixVectorMultiply.compute(MATRIX, VECTOR));
    }
}
