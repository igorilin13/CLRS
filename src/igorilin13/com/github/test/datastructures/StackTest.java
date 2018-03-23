package igorilin13.com.github.test.datastructures;

import igorilin13.com.github.main.datastructures.Stack;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class StackTest {
    private static final int TOTAL_ITEMS = 10000;

    private Stack<Integer> stack;

    @Before
    public void setUp() {
        stack = new Stack<>();
        for (int i = 0; i < TOTAL_ITEMS; i++) {
            stack.push(i);
        }
    }

    @Test
    public void testIsEmpty() {
        Stack<Integer> stack = new Stack<>();
        assertTrue(stack.isEmpty());
        stack.push(13);
        assertFalse(stack.isEmpty());
    }

    @Test
    public void testPop() {
        for (int i = TOTAL_ITEMS - 1; i >= 0; i--) {
            assertEquals(i, (int) stack.pop());
        }
    }

    @Test
    public void testMultiPop() {
        int popTotal = TOTAL_ITEMS / 2;
        List<Integer> result = stack.multiPop(popTotal);
        assertEquals(popTotal, result.size());
        for (int i = TOTAL_ITEMS - 1; i >= popTotal; i--) {
            assertEquals(i, (int) result.get(TOTAL_ITEMS - 1 - i));
        }
    }

    @Test
    public void testPeek() {
        for (int i = TOTAL_ITEMS - 1; i >= 0; i--) {
            assertEquals(i, (int) stack.peek());
            assertEquals(i + 1, stack.size());
            stack.pop();
        }
    }

    @Test
    public void testPeekFromTop() {
        for (int i = 0; i < TOTAL_ITEMS; i++) {
            assertEquals(TOTAL_ITEMS - i - 1, (int) stack.peekFromTop(i));
        }
    }

    @Test
    public void testToList() {
        List<Integer> list = stack.toList();
        assertEquals(TOTAL_ITEMS, list.size());
        for (int i = 0; i < TOTAL_ITEMS; i++) {
            assertEquals(i, (int) list.get(i));
        }
    }
}
