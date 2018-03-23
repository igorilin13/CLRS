package igorilin13.com.github.test.approximation;

import igorilin13.com.github.main.approximation.Max3CnfSatAlgorithm;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class Max3CnfSatAlgorithmTest {
    private static final String FORMULA = "(x1 + !x2 + x4)(!x1 + x3 + !x5)(x2 + !x4 + x6)";
    private static final int TOTAL_CLAUSES = 3;
    private static final int MIN_EXPECTED_TRUE_CLAUSES = 7 * TOTAL_CLAUSES / 8;

    @Test
    public void testMac3CnfSat() {
        Max3CnfSatAlgorithm algorithm = new Max3CnfSatAlgorithm(FORMULA);
        assertTrue(algorithm.getNumberOfTrueClauses() >= MIN_EXPECTED_TRUE_CLAUSES);
    }
}
