package igorilin13.com.github.test.parallel;

import igorilin13.com.github.main.parallel.FibNumbers;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class FibNumbersTest {
    private static final int SIZE = 25;
    private static final int[] EXPECTED_RESULT = new int[SIZE];

    @BeforeClass
    public static void setUpClass() {
        EXPECTED_RESULT[0] = 0;
        EXPECTED_RESULT[1] = 1;
        for (int i = 2; i < SIZE; i++) {
            EXPECTED_RESULT[i] = EXPECTED_RESULT[i - 1] + EXPECTED_RESULT[i - 2];
        }
    }

    @Test
    public void testParallel() {
        int[] result = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            result[i] = FibNumbers.compute(i);
        }
        assertArrayEquals(EXPECTED_RESULT, result);
    }
}
