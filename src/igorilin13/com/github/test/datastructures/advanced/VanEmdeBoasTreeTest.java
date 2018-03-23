package igorilin13.com.github.test.datastructures.advanced;

import igorilin13.com.github.main.datastructures.advanced.VanEmdeBoasTree;
import igorilin13.com.github.test.RandomValueGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class VanEmdeBoasTreeTest {
    private static final int UNIVERSE_SIZE = 1024;
    private final List<Integer> testData = new ArrayList<>();

    private VanEmdeBoasTree tree;

    @Before
    public void setUp() {
        testData.clear();
        tree = new VanEmdeBoasTree(UNIVERSE_SIZE);

        for (int i = 0; i < UNIVERSE_SIZE; i++) {
            if (RandomValueGenerator.randomBoolean()) {
                testData.add(i);
                tree.insert(i);
            }
        }
    }

    @Test
    public void testMin() {
        assertEquals(testData.get(0), tree.min());
    }

    @Test
    public void testMax() {
        assertEquals(testData.get(testData.size() - 1), tree.max());
    }

    @Test
    public void testContains() {
        for (int i = 0; i < UNIVERSE_SIZE; i++) {
            assertEquals(testData.contains(i), tree.contains(i));
        }
    }

    @Test
    public void testSuccessor() {
        for (int i = 0; i < testData.get(0); i++) {
            assertEquals(testData.get(0), tree.successor(i));
        }
        for (int i = 0; i < testData.size() - 1; i++) {
            for (int j = testData.get(i); j < testData.get(i + 1); j++) {
                assertEquals(testData.get(i + 1), tree.successor(j));
            }
        }
        for (int i = testData.get(testData.size() - 1); i < UNIVERSE_SIZE; i++) {
            assertNull(tree.successor(i));
        }
    }

    @Test
    public void testPredecessor() {
        for (int i = 0; i <= testData.get(0); i++) {
            assertNull(tree.predecessor(i));
        }
        for (int i = 0; i < testData.size() - 1; i++) {
            for (int j = testData.get(i) + 1; j <= testData.get(i + 1); j++) {
                assertEquals(testData.get(i), tree.predecessor(j));
            }
        }
        for (int i = testData.get(testData.size() - 1) + 1; i < UNIVERSE_SIZE; i++) {
            assertEquals(testData.get(testData.size() - 1), tree.predecessor(i));
        }
    }

    @Test
    public void testDelete() {
        while (testData.size() > 0) {
            while (!testData.isEmpty()) {
                int keyIndex = RandomValueGenerator.randomInt(0, testData.size() - 1);
                int item = testData.get(keyIndex);
                testData.remove(keyIndex);

                tree.delete(item);
                assertFalse(tree.contains(item));
                assertEquals(testData.size() > 0 ? testData.get(0) : null, tree.min());
                assertEquals(testData.size() > 0 ? testData.get(testData.size() - 1) : null, tree.max());
            }
        }
    }
}
