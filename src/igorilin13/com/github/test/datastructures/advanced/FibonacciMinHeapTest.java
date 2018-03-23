package igorilin13.com.github.test.datastructures.advanced;

import igorilin13.com.github.main.datastructures.advanced.FibonacciMinHeap;
import igorilin13.com.github.test.RandomValueGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FibonacciMinHeapTest {
    private static final int TEST_DATA_SIZE = 10000;
    private final List<Integer> testData = new ArrayList<>(TEST_DATA_SIZE);

    private FibonacciMinHeap<Integer> heap;

    @Before
    public void setUp() {
        testData.clear();
        for (int i = 0; i < TEST_DATA_SIZE; i++) {
            testData.add(i);
        }
        Collections.shuffle(testData);

        heap = createMinHeapWith(testData);
    }

    private FibonacciMinHeap<Integer> createMinHeapWith(List<Integer> data) {
        FibonacciMinHeap<Integer> res = new FibonacciMinHeap<>();
        for (int i = 0; i < data.size(); i++) {
            res.insert(data.get(i));
            assertEquals(i + 1, res.size());
        }
        return res;
    }

    @Test
    public void testMinExtract() {
        for (int i = 0; i < TEST_DATA_SIZE; i++) {
            assertEquals(i, (int) heap.min());
            assertEquals(i, (int) heap.extractMin());
            assertEquals(TEST_DATA_SIZE - i - 1, heap.size());
        }
        assertNull(heap.extractMin());
        assertEquals(0, heap.size());
    }

    @Test
    public void testUnion() {
        FibonacciMinHeap<Integer> secondHeap = createMinHeapWith(testData);
        heap.union(secondHeap);
        for (int i = 0; i < TEST_DATA_SIZE; i++) {
            assertEquals(i, (int) heap.extractMin());
            assertEquals(i, (int) heap.extractMin());
        }
    }

    @Test
    public void testContains() {
        for (int i = -TEST_DATA_SIZE; i < TEST_DATA_SIZE; i++) {
            assertEquals(i >= 0, heap.contains(i));
        }
    }

    @Test
    public void testDecreaseKey() {
        while (!testData.isEmpty()) {
            int keyIndex = RandomValueGenerator.randomInt(0, testData.size() - 1);
            int newKey = Collections.min(testData) - 1;
            heap.decreaseKey(testData.get(keyIndex), newKey);
            assertEquals(newKey, (int) heap.extractMin());
            testData.remove(keyIndex);
        }
    }

    @Test
    public void testDelete() {
        while (!testData.isEmpty()) {
            assertEquals(Collections.min(testData), heap.min());
            assertEquals(testData.size(), heap.size());

            int keyIndex = RandomValueGenerator.randomInt(0, testData.size() - 1);
            heap.delete(testData.get(keyIndex));
            testData.remove(keyIndex);
        }
    }
}
