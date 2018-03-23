package igorilin13.com.github.test.graph.algorithms;

import igorilin13.com.github.main.graph.Graph;
import igorilin13.com.github.main.graph.algorithms.elementary.TopologicalSort;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class TopologicalSortTest extends BaseGraphTest {
    private static final int[][] ADJ_MATRIX = new int[][]{
            {0, 1, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 1, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 1, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    @Override
    protected int[][] getAdjMatrix() {
        return ADJ_MATRIX;
    }

    @Test
    public void testTopologicalSort() {
        List<Graph.Vertex<Integer>> result = new TopologicalSort<>(graph).getResult();
        Map<Integer, Graph.Vertex<Integer>> resultNames = new HashMap<>();
        for (Graph.Vertex<Integer> vertex : result) {
            resultNames.put(vertex.getName(), vertex);
        }

        for (int i = 0; i < ADJ_MATRIX.length; i++) {
            int indexInSorted = result.indexOf(resultNames.get(i));
            for (int j = 0; j < ADJ_MATRIX.length; j++) {
                if (ADJ_MATRIX[i][j] != 0) {
                    assertTrue(result.indexOf(resultNames.get(j)) > indexInSorted);
                }
            }
        }
    }
}
