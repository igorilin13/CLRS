package igorilin13.com.github.test.parallel;

import igorilin13.com.github.main.parallel.SquareMatrixMultiply;
import igorilin13.com.github.test.RandomValueGenerator;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class SquareMatrixMultiplyTest {
    private static final int SIZE = 250;
    private static final int[][] FIRST = new int[SIZE][SIZE];
    private static final int[][] SECOND = new int[SIZE][SIZE];
    private static final int[][] EXPECTED_RESULT = new int[SIZE][SIZE];

    @BeforeClass
    public static void setUpClass() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                FIRST[i][j] = RandomValueGenerator.randomInt();
                SECOND[i][j] = RandomValueGenerator.randomInt();
            }
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    EXPECTED_RESULT[i][j] += FIRST[i][k] * SECOND[i][j];
                }
            }
        }
    }

    @Test
    public void testParallel() {
        assertArrayEquals(EXPECTED_RESULT, SquareMatrixMultiply.compute(FIRST, SECOND));
    }
}
