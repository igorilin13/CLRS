package igorilin13.com.github.test.datastructures;

import igorilin13.com.github.main.datastructures.Queue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueueTest {
    private Queue<Integer> queue;

    @Before
    public void setUp() {
        queue = new Queue<>();
    }

    @Test
    public void testIsEmpty() {
        assertTrue(queue.isEmpty());
        queue.enqueue(13);
        assertFalse(queue.isEmpty());
    }

    @Test
    public void testEnqueueDequeue() {
        final int totalItems = 10000;
        for (int i = 0; i < totalItems; i++) {
            queue.enqueue(i);
        }
        for (int i = 0; i < totalItems; i++) {
            assertEquals(i, (int) queue.dequeue());
        }
    }
}
