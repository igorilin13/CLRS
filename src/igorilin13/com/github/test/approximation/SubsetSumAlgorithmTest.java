package igorilin13.com.github.test.approximation;

import igorilin13.com.github.main.approximation.SubsetSumAlgorithm;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SubsetSumAlgorithmTest {
    private static final Set<Integer> VALUES = new HashSet<>(Arrays.asList(104, 102, 201, 101));
    private static final int TARGET_SUM = 308;
    private static final int OPTIMAL_SUM = 307;
    private static final double APPROXIMATION_FACTOR = 0.4;

    private SubsetSumAlgorithm algorithm = new SubsetSumAlgorithm(VALUES, TARGET_SUM, APPROXIMATION_FACTOR);

    @Test
    public void testExactSum() {
        assertEquals(OPTIMAL_SUM, algorithm.computeExactSum());
    }

    @Test
    public void testApproximationSum() {
        int approximationSum = algorithm.computeApproximationSum();
        assertTrue(approximationSum <= TARGET_SUM);
        assertTrue(1.0 - (double) approximationSum / OPTIMAL_SUM <= APPROXIMATION_FACTOR);
    }
}
