package igorilin13.com.github.test.approximation;

import igorilin13.com.github.main.approximation.WeightedVertexCoverAlgorithm;
import igorilin13.com.github.main.approximation.WeightedVertexCoverAlgorithm.WeightedVertex;
import igorilin13.com.github.main.graph.Graph;
import igorilin13.com.github.test.GraphAssert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class WeightedVertexCoverAlgorithmTest {
    private static final int[][] ADJ_MATRIX = {
            {0, 0, 0, 1, 1, 1},
            {0, 0, 0, 1, 1, 1},
            {0, 0, 0, 1, 1, 1},
            {1, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 0}

    };

    private static final int[] VERTEX_WEIGHTS = {6, 2, 4, 5, 3, 2};
    private static final int OPTIMAL_SOLUTION_WEIGHT = 10;
    private static final int APPROXIMATION_COMPLEXITY = 2;

    private Graph<WeightedVertex<Integer>> graph;

    @Before
    public void setUp() {
        Graph.GraphBuilder<WeightedVertex<Integer>> builder = new Graph.GraphBuilder<>();

        Map<Integer, WeightedVertex<Integer>> vertexNames = new HashMap<>();
        for (int i = 0; i < VERTEX_WEIGHTS.length; i++) {
            WeightedVertex<Integer> name = new WeightedVertex<>(i, VERTEX_WEIGHTS[i]);
            vertexNames.put(i, new WeightedVertex<>(i, VERTEX_WEIGHTS[i]));
            builder.addVertex(name);
        }

        for (int i = 0; i < ADJ_MATRIX.length; i++) {
            for (int j = 0; j < ADJ_MATRIX[i].length; j++) {
                if (ADJ_MATRIX[i][j] > 0) {
                    builder.addEdge(vertexNames.get(i), vertexNames.get(j), 1);
                }
            }
        }

        graph = builder.build();
    }

    @Test
    public void testWeightedVertexCover() {
        WeightedVertexCoverAlgorithm<Integer> algorithm = new WeightedVertexCoverAlgorithm<>(graph);
        GraphAssert.assertVertexCover(graph, algorithm.getVertexCover());
        assertTrue(algorithm.getVertexCoverWeight() <= APPROXIMATION_COMPLEXITY * OPTIMAL_SOLUTION_WEIGHT);
    }
}
