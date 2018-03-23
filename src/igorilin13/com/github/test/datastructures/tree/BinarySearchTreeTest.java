package igorilin13.com.github.test.datastructures.tree;

import igorilin13.com.github.main.datastructures.tree.BinarySearchTree;

public class BinarySearchTreeTest extends BaseBinarySearchTreeTest {
    @Override
    BinarySearchTree<Integer> createTree() {
        return new BinarySearchTree<>();
    }
}
