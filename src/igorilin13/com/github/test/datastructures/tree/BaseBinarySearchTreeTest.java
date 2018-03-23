package igorilin13.com.github.test.datastructures.tree;

import igorilin13.com.github.main.datastructures.tree.BinarySearchTree;
import igorilin13.com.github.main.datastructures.tree.TreeNode;
import igorilin13.com.github.main.util.ListUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public abstract class BaseBinarySearchTreeTest {
    private final static int MIN_KEY = 0;
    final static int MAX_KEY = 5000;
    private final static int REPEATING_INTERVAL = 13;

    BinarySearchTree<Integer> tree;
    ArrayList<Integer> keys;
    private int expectedSize;

    @Before
    public void setUp() {
        keys = new ArrayList<>();
        for (int i = MIN_KEY; i <= MAX_KEY; i++) {
            keys.add(i);
            if ((i - MIN_KEY) % REPEATING_INTERVAL == 0) {
                keys.add(i);
            }
        }
        Collections.shuffle(keys);

        tree = createTree();
        expectedSize = 0;
        for (int key : keys) {
            modifyAndCheckStructure(() -> tree.insert(key), 1);
        }
        Collections.sort(keys);
    }

    abstract BinarySearchTree<Integer> createTree();

    @Test
    public void testInOrderWalk() {
        assertEquals(ListUtils.toString(keys), tree.inOrderWalk().toString());
        for (int i = MIN_KEY; i <= MAX_KEY; i++) {
            final int key = i;
            modifyAndCheckStructure(() -> tree.delete(key), -1);
            keys.remove(Integer.valueOf(i));
            assertEquals(ListUtils.toString(keys), tree.inOrderWalk().toString());
        }
    }

    @Test
    public void testMin() {
        for (int i = MIN_KEY; i <= MAX_KEY; i++) {
            assertEquals(i, (int) tree.minKey());
            final int key = i;
            modifyAndCheckStructure(() -> tree.deleteAll(key), -Collections.frequency(keys, i));
        }
        assertNull(tree.minKey());
    }

    @Test
    public void testPollMin() {
        for (int i = MIN_KEY; i <= MAX_KEY; i++) {
            while (tree.minKey() != null && i == tree.minKey()) {
                final int key = i;
                modifyAndCheckStructure(() -> assertEquals(key, (int) tree.pollMinKey()), -1);
            }
        }
        assertNull(tree.minKey());
    }

    @Test
    public void testMax() {
        for (int i = MAX_KEY; i >= MIN_KEY; i--) {
            assertEquals(i, (int) tree.maxKey());
            final int key = i;
            modifyAndCheckStructure(() -> tree.deleteAll(key), -Collections.frequency(keys, i));
        }
        assertNull(tree.minKey());
    }

    @Test
    public void testPollMax() {
        for (int i = MAX_KEY; i >= MIN_KEY; i--) {
            while (tree.maxKey() != null && i == tree.maxKey()) {
                final int key = i;
                modifyAndCheckStructure(() -> assertEquals(key, (int) tree.pollMaxKey()), -1);
            }
        }
        assertNull(tree.maxKey());
    }

    @Test
    public void testPrevKey() {
        assertNull(tree.prevKey(MIN_KEY));
        for (int i = MIN_KEY + 1; i <= MAX_KEY; i++) {
            assertEquals(i - 1, (int) tree.prevKey(i));
        }
    }

    @Test
    public void testNextKey() {
        for (int i = MIN_KEY; i < MAX_KEY; i++) {
            assertEquals(i + 1, (int) tree.nextKey(i));
        }
        assertNull(tree.nextKey(MAX_KEY));
    }

    @Test
    public void testSearch() {
        for (int i = MIN_KEY; i <= MAX_KEY; i++) {
            int searchKey = i + (MAX_KEY - i) / 2;
            assertEquals(searchKey, (int) tree.recursiveSearch(searchKey).getKey());
            assertEquals(searchKey, (int) tree.iterativeSearch(searchKey).getKey());
            final int key = i;
            modifyAndCheckStructure(() -> tree.deleteAll(key), -Collections.frequency(keys, i));
        }
    }

    @Test
    public void testIsEmpty() {
        assertFalse(tree.isEmpty());
        for (int i = MIN_KEY; i <= MAX_KEY; i++) {
            final int key = i;
            modifyAndCheckStructure(() -> tree.deleteAll(key), -Collections.frequency(keys, i));
        }
        assertTrue(tree.isEmpty());
    }

    @Test
    public void testCount() {
        for (int i = MIN_KEY; i <= MAX_KEY; i++) {
            assertEquals(Collections.frequency(keys, i), tree.count(i));
        }
    }

    private void modifyAndCheckStructure(Runnable modifyTree, int expectedSizeDiff) {
        modifyTree.run();
        expectedSize += expectedSizeDiff;
        assertEquals(expectedSize, tree.size());
        assertTrue(checkBinarySearchTreeProperty(tree.getRoot()));
        assertTrue(checkAdditionalProperties(tree.getRoot()));
    }

    boolean checkAdditionalProperties(TreeNode<Integer> root) {
        return true;
    }

    private boolean checkBinarySearchTreeProperty(TreeNode<Integer> node) {
        if (node == null) {
            return true;
        }
        TreeNode<Integer> leftChild = node.getLeft();
        TreeNode<Integer> rightChild = node.getRight();
        if ((leftChild != null && leftChild.getKey().compareTo(node.getKey()) > 0)
                || (rightChild != null && rightChild.getKey().compareTo(node.getKey()) < 0)) {
            return false;
        }
        return checkBinarySearchTreeProperty(leftChild) && checkBinarySearchTreeProperty(rightChild);
    }
}
