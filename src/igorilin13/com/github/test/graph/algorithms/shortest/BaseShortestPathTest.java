package igorilin13.com.github.test.graph.algorithms.shortest;

import igorilin13.com.github.main.graph.algorithms.shortest.BaseShortestPathAlgorithm;
import igorilin13.com.github.test.graph.algorithms.BaseGraphTest;

import static org.junit.Assert.assertEquals;

abstract class BaseShortestPathTest extends BaseGraphTest {
    void verifyPaths(String[] expectedPaths, int[] expectedWeights, BaseShortestPathAlgorithm<Integer> result) {
        for (int i = 0; i < expectedPaths.length; i++) {
            assertEquals(expectedPaths[i], result.getPathAsString(i));
            assertEquals(expectedWeights[i], result.getPathWeight(i));
        }
    }
}
