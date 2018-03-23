package igorilin13.com.github.test.datastructures;

import igorilin13.com.github.main.datastructures.BinaryHeap;
import igorilin13.com.github.main.util.Range;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static igorilin13.com.github.main.datastructures.BinaryHeap.parent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BinaryHeapTest {
    private static final int MIN_KEY = 0;
    private static final int MAX_KEY = 1000;

    private List<Integer> keys;

    @Before
    public void setUp() {
        keys = Range.ofInt(MIN_KEY, MAX_KEY + 1);
        Collections.shuffle(keys);
    }

    @Test
    public void testMinHeap() {
        assertHeapStructure(BinaryHeap.createMinHeap(keys));
    }

    @Test
    public void testMaxHeap() {
        assertHeapStructure(BinaryHeap.createMaxHeap(keys));
    }

    private void assertHeapStructure(BinaryHeap<Integer> heap) {
        assertEquals(keys.size(), heap.size());
        BinaryHeap.Type type = heap.getType();
        for (int i = 1; i < keys.size(); i++) {
            if (type == BinaryHeap.Type.MAX) {
                assertTrue(keys.get(parent(i)) >= keys.get(i));
            } else {
                assertTrue(keys.get(parent(i)) <= keys.get(i));
            }
        }
    }
}
