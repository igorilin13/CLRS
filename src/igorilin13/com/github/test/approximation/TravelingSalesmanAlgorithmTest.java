package igorilin13.com.github.test.approximation;

import igorilin13.com.github.main.approximation.TravelingSalesmanAlgorithm;
import igorilin13.com.github.main.graph.Graph;
import igorilin13.com.github.test.graph.algorithms.BaseGraphTest;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class TravelingSalesmanAlgorithmTest extends BaseGraphTest {
    private static final int[][] ADJ_MATRIX = {
            {0, 8, 7, 9},
            {8, 0, 6, 12},
            {7, 6, 0, 7},
            {9, 12, 7, 0},
    };

    private static final int OPTIMAL_SOLUTION_WEIGHT = 30;
    private static final int APPROXIMATION_COMPLEXITY = 2;

    @Test
    public void testTspAlgorithm() {
        TravelingSalesmanAlgorithm<Integer> algorithm = new TravelingSalesmanAlgorithm<>(graph);
        assertTrue(isHamiltonianCycle(algorithm.getHamiltonianCycle()));
        assertTrue(algorithm.getPathWeight() <= APPROXIMATION_COMPLEXITY * OPTIMAL_SOLUTION_WEIGHT);
    }

    private boolean isHamiltonianCycle(List<Graph.Vertex<Integer>> list) {
        Collection<Graph.Vertex<Integer>> vertices = graph.getVerticesByName().values();
        return vertices.size() == list.size() && vertices.containsAll(list);
    }

    @Override
    protected int[][] getAdjMatrix() {
        return ADJ_MATRIX;
    }
}
