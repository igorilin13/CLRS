package igorilin13.com.github.test.graph.algorithms.shortest.all;

import igorilin13.com.github.main.graph.algorithms.shortest.all.TransitiveClosure;
import igorilin13.com.github.test.graph.algorithms.BaseGraphTest;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class TransitiveClosureTest extends BaseGraphTest {
    private static final int[][] ADJ_MATRIX = {
            {1, 0, 0, 0},
            {0, 1, 1, 1},
            {0, 1, 1, 0},
            {1, 0, 1, 1}
    };

    private static final int[][] EXPECTED_RESULT = {
            {1, 0, 0, 0},
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1}
    };

    @Test
    public void transitiveClosure() {
        assertArrayEquals(EXPECTED_RESULT, new TransitiveClosure<>(graph).getResult());
    }

    @Override
    protected int[][] getAdjMatrix() {
        return ADJ_MATRIX;
    }
}
