package igorilin13.com.github.test.graph.algorithms;

import igorilin13.com.github.main.graph.algorithms.maxflow.EdmondsKarpMaxFlow;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EdmondsKarpMaxFlowTest extends BaseGraphTest {
    private static final int[][] ADJ_MATRIX = {
            {0, 16, 13, 0, 0, 0},
            {0, 0, 0, 12, 0, 0},
            {0, 4, 0, 0, 14, 0},
            {0, 0, 9, 0, 0, 20},
            {0, 0, 0, 7, 0, 4},
            {0, 0, 0, 0, 0, 0}
    };

    private static final int[][] EXPECTED_FLOW = {
            {0, 11, 12, 0, 0, 0},
            {0, 0, 0, 12, 0, 0},
            {0, 1, 0, 0, 11, 0},
            {0, 0, 0, 0, 0, 19},
            {0, 0, 0, 7, 0, 4},
            {0, 0, 0, 0, 0, 0}
    };

    @Test
    public void testEdmondsKarpMaxFlow() {
        EdmondsKarpMaxFlow<Integer> result = new EdmondsKarpMaxFlow<>(graph, 0, ADJ_MATRIX.length - 1);
        verifyFlow(EXPECTED_FLOW, result.getResultFlow());
    }

    private void verifyFlow(int[][] expected, int[][] result) {
        int size = expected.length;
        assertEquals(size, result.length);
        int expFromSource = 0;
        int expIntoDest = 0;
        int resFromSource = 0;
        int resIntoDest = 0;
        for (int i = 0; i < size; i++) {
            expFromSource += expected[0][i];
            expIntoDest += expected[i][size - 1];
            resFromSource += result[0][i];
            resIntoDest += result[i][size - 1];
            if (i > 0 && i < size - 1) {
                int internalFlowIn = 0;
                int internalFlowOut = 0;
                for (int j = 0; j < size; j++) {
                    internalFlowIn += result[j][i];
                    internalFlowOut += result[i][j];
                }
                assertEquals(internalFlowIn, internalFlowOut);
            }
        }
        assertEquals(expFromSource, resFromSource);
        assertEquals(expIntoDest, resIntoDest);
    }

    @Override
    protected int[][] getAdjMatrix() {
        return ADJ_MATRIX;
    }
}
