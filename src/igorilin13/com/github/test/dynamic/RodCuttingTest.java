package igorilin13.com.github.test.dynamic;

import igorilin13.com.github.main.dynamic.RodCutting;
import igorilin13.com.github.main.util.TupleN;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class RodCuttingTest {
    private static final int[] TEST_ROD = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
    private static final int EXPECTED_MAX_PRICE = 30;
    private static final String EXPECTED_SOLUTION = "10";
    private static final int[] EXPECTED_MAX_PRICE_ARRAY = {0, 1, 5, 8, 10, 13, 17, 18, 22, 25, 30};
    private static final int[] EXPECTED_OPTIMAL_FIRST_PIECE = {0, 1, 2, 3, 2, 2, 6, 1, 2, 3, 10};

    @Test
    public void testCutRod() {
        assertEquals(EXPECTED_MAX_PRICE, RodCutting.cutRod(TEST_ROD));
    }

    @Test
    public void testMemoizedCutRod() {
        assertEquals(EXPECTED_MAX_PRICE, RodCutting.memoizedCutRod(TEST_ROD));
    }

    @Test
    public void testBottomUpCutRod() {
        assertEquals(EXPECTED_MAX_PRICE, RodCutting.bottomUpCutRod(TEST_ROD));
    }

    @Test
    public void testExtendedBottomUpCutRod() {
        TupleN<int[]> res = RodCutting.extendedBottomUpCutRod(TEST_ROD);
        assertArrayEquals(EXPECTED_MAX_PRICE_ARRAY, res.get(0));
        assertArrayEquals(EXPECTED_OPTIMAL_FIRST_PIECE, res.get(1));
    }

    @Test
    public void testGetCutRodSolution() {
        assertEquals(EXPECTED_SOLUTION, RodCutting.getCutRodSolution(TEST_ROD));
    }
}
