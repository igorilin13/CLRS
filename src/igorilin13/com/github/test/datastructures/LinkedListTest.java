package igorilin13.com.github.test.datastructures;

import igorilin13.com.github.main.datastructures.LinkedList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinkedListTest {
    private LinkedList<Integer> list;

    @Before
    public void setUp() {
        list = new LinkedList<>();
    }

    @Test
    public void testInsertSearchDeleteSearch() {
        final int totalNodes = 10;
        for (int i = 0; i < totalNodes; i++) {
            list.insert(i);
        }
        for (int i = 0; i < totalNodes; i++) {
            LinkedList.Node<Integer> node = list.search(i);
            assertNotNull(node);
            assertEquals((int) node.getKey(), i);
        }
        for (int i = 0; i < totalNodes; i++) {
            list.delete(i);
        }
        for (int i = 0; i < totalNodes; i++) {
            assertNull(list.search(i));
        }
    }
}
