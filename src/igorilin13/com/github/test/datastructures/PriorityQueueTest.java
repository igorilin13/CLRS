package igorilin13.com.github.test.datastructures;

import igorilin13.com.github.main.datastructures.BinaryHeap;
import igorilin13.com.github.main.datastructures.PriorityQueue;
import igorilin13.com.github.main.util.Range;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PriorityQueueTest {
    private static final int MIN_KEY = 0;
    private static final int MAX_KEY = 1000;

    private List<Integer> keys;

    private PriorityQueue<Integer> priorityQueue;

    @Before
    public void setUp() {
        keys = Range.ofInt(MIN_KEY, MAX_KEY + 1);
    }

    private void initQueue(BinaryHeap.Type type) {
        priorityQueue = type == BinaryHeap.Type.MAX ? PriorityQueue.createMax() : PriorityQueue.createMin();

        Collections.shuffle(keys);
        for (int key : keys) {
            priorityQueue.insert(key);
        }
        Collections.sort(keys);
    }

    @Test
    public void testSize() {
        testSize(BinaryHeap.Type.MIN);
        testSize(BinaryHeap.Type.MAX);
    }

    private void testSize(BinaryHeap.Type type) {
        initQueue(type);
        assertEquals(keys.size(), priorityQueue.size());
    }

    @Test
    public void testPeekPoll() {
        testPeekPoll(BinaryHeap.Type.MIN);
        testPeekPoll(BinaryHeap.Type.MAX);
    }

    private void testPeekPoll(BinaryHeap.Type type) {
        initQueue(type);
        if (type == BinaryHeap.Type.MAX) {
            Collections.reverse(keys);
        }
        for (int key : keys) {
            assertEquals(key, (int) priorityQueue.peek());
            assertEquals(key, (int) priorityQueue.poll());
        }
    }

    @Test
    public void testIncreasePriority() {
        testIncreasePriority(BinaryHeap.Type.MIN);
        testIncreasePriority(BinaryHeap.Type.MAX);
    }

    private void testIncreasePriority(BinaryHeap.Type type) {
        initQueue(type);

        int index = keys.size() / 2;
        int prevKey = keys.get(index);
        int newKey = prevKey + (type == BinaryHeap.Type.MAX ? MAX_KEY : -MAX_KEY);
        priorityQueue.increasePriority(index, newKey);

        assertEquals(newKey, (int) priorityQueue.peek());
    }
}
