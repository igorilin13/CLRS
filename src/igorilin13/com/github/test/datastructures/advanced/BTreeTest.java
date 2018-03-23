package igorilin13.com.github.test.datastructures.advanced;

import igorilin13.com.github.main.datastructures.advanced.BTree;
import igorilin13.com.github.main.util.ListUtils;
import igorilin13.com.github.test.RandomValueGenerator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class BTreeTest {
    private static final String TEST_DATA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int MIN_DEGREE = 2;
    private static final int MAX_DEGREE = 13;

    private final List<String> currentTestData = new ArrayList<>();

    @Test
    public void testWalk() {
        baseTreeTest(tree -> {
            Collections.sort(currentTestData);
            assertEquals(ListUtils.toString(currentTestData), tree.inOrderWalk());
        });
    }

    @Test
    public void testContains() {
        baseTreeTest(tree -> {
            for (int i = 0; i < TEST_DATA.length(); i++) {
                assertTrue(tree.contains(TEST_DATA.charAt(i) + ""));
            }
            assertFalse(tree.contains(""));
            assertFalse(tree.contains(TEST_DATA));
        });
    }

    @Test
    public void testDelete() {
        baseTreeTest(tree -> {
            Collections.sort(currentTestData);
            while (currentTestData.size() > 0) {
                int index = RandomValueGenerator.randomInt(0, currentTestData.size() - 1);
                final String item = currentTestData.get(index);
                currentTestData.remove(index);
                tree.delete(item);
                checkBTreeStructure(true, tree.root, tree.getDegree());
                assertEquals(ListUtils.toString(currentTestData), tree.inOrderWalk());
            }
        });
    }

    private void baseTreeTest(Consumer<BTree<String>> test) {
        for (int degree = MIN_DEGREE; degree <= MAX_DEGREE; degree++) {
            resetCurrentTestData();
            BTree<String> tree = new BTree<>(degree);
            for (String val : currentTestData) {
                tree.insert(val);
                checkBTreeStructure(true, tree.root, degree);
            }
            test.accept(tree);
        }
    }

    private void resetCurrentTestData() {
        currentTestData.clear();
        currentTestData.addAll(Arrays.asList(TEST_DATA.split("")));
        Collections.shuffle(currentTestData);
    }

    private void checkBTreeStructure(boolean root, BTree.Node<String> node, int degree) {
        int size = node.size();
        if (!root) {
            assertTrue(size >= degree - 1);
        }
        assertTrue(size <= 2 * degree - 1);
        assertEquals(size, node.getKeys().size());

        for (int i = 0; i < size - 1; i++) {
            assertTrue(node.key(i).compareTo(node.key(i + 1)) <= 0);
        }

        if (!node.isLeaf()) {
            assertEquals(size + 1, node.getChildren().size());
            for (int i = 0; i <= size; i++) {
                List<String> childKeys = node.child(i).getKeys();
                for (String key : childKeys) {
                    if (i != 0) {
                        assertTrue(node.key(i - 1).compareTo(key) <= 0);
                    }
                    if (i != size) {
                        assertTrue(node.key(i).compareTo(key) >= 0);
                    }
                }
                checkBTreeStructure(false, node.child(i), degree);
            }
        }
    }
}
