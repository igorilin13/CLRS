package igorilin13.com.github.test.datastructures.tree;

import igorilin13.com.github.main.datastructures.tree.BinarySearchTree;
import igorilin13.com.github.main.datastructures.tree.RedBlackTree;
import igorilin13.com.github.main.datastructures.tree.TreeNode;

import static igorilin13.com.github.main.datastructures.tree.RedBlackTree.Color.BLACK;
import static igorilin13.com.github.main.datastructures.tree.RedBlackTree.Color.RED;

public class RedBlackTreeTest extends BaseBinarySearchTreeTest {
    private int height;

    @Override
    BinarySearchTree<Integer> createTree() {
        return new RedBlackTree<>();
    }

    @Override
    boolean checkAdditionalProperties(TreeNode<Integer> root) {
        height = -1;
        return checkRedBlackProperties((RedBlackTree.RedBlackTreeNode) root, 0);
    }

    private boolean checkRedBlackProperties(RedBlackTree.RedBlackTreeNode node, int blackNodesOnPath) {
        if (node == null) {
            return true;
        }
        if (node.getColor() == RED && (node.getParent() == null || node.getParent().getColor() == RED)) {
            return false;
        }
        if (node.getColor() == BLACK) {
            blackNodesOnPath++;
        }
        if (node.getLeft() == null || node.getRight() == null) {
            if (height < 0) {
                height = blackNodesOnPath;
            } else if (height != blackNodesOnPath) {
                return false;
            }
        }
        return checkRedBlackProperties(node.getLeft(), blackNodesOnPath)
                && checkRedBlackProperties(node.getRight(), blackNodesOnPath);
    }
}
