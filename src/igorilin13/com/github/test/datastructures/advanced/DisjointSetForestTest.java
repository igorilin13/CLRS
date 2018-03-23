package igorilin13.com.github.test.datastructures.advanced;

import igorilin13.com.github.main.datastructures.advanced.DisjointSetForest;
import org.junit.Before;
import org.junit.Test;

import java.util.function.BiConsumer;

import static org.junit.Assert.assertTrue;

public class DisjointSetForestTest {
    private static final int GRAPH_SIZE = 10;
    private static final int[][] CONNECTED_COMPONENTS = {
            {0, 1, 2, 3},
            {4, 5, 6},
            {7, 8},
            {9}
    };

    private DisjointSetForest<Integer> disjointSet;

    @Before
    public void setUp() {
        disjointSet = new DisjointSetForest<>();
        for (int i = 0; i < GRAPH_SIZE; i++) {
            disjointSet.makeSet(i);
        }
    }

    @Test
    public void testConnectedComponents() {
        forEachEdge((u, v) -> {
            if (disjointSet.findSet(u) != disjointSet.findSet(v)) {
                disjointSet.union(u, v);
            }
        });
        forEachEdge((u, v) -> assertTrue(disjointSet.findSet(u) == disjointSet.findSet(v)));
    }

    private void forEachEdge(BiConsumer<Integer, Integer> action) {
        for (int[] CONNECTED_COMPONENT : CONNECTED_COMPONENTS) {
            for (int j = 1; j < CONNECTED_COMPONENT.length; j++) {
                action.accept(CONNECTED_COMPONENT[j - 1], CONNECTED_COMPONENT[j]);
            }
        }
    }
}
