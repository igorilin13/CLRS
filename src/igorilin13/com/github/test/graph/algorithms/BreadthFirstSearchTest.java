package igorilin13.com.github.test.graph.algorithms;

import igorilin13.com.github.main.graph.algorithms.elementary.BreadthFirstSearch;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BreadthFirstSearchTest extends BaseGraphTest {
    private static final int SOURCE_VERTEX = 5;

    private static final int[][] ADJ_MATRIX = {
            {0, 0, 1, 0, 0, 0, 0, 1},
            {0, 0, 1, 1, 0, 0, 0, 1},
            {1, 1, 0, 1, 0, 0, 0, 1},
            {0, 1, 1, 0, 1, 0, 0, 0},
            {0, 0, 0, 1, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {1, 1, 1, 0, 0, 0, 0, 0}
    };

    private static final String[] EXPECTED_PATH = {
            "54320", "5431", "5432", "543", "54", "5", "56", "54317"
    };

    @Override
    protected int[][] getAdjMatrix() {
        return ADJ_MATRIX;
    }

    @Test
    public void testBfs() {
        BreadthFirstSearch<Integer> bfs = new BreadthFirstSearch<>(graph, SOURCE_VERTEX);
        for (int i = 0; i < ADJ_MATRIX.length; i++) {
            assertEquals(EXPECTED_PATH[i], bfs.getPathAsString(i));
        }
    }
}
