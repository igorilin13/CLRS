package igorilin13.com.github.test.datastructures.tree;

import igorilin13.com.github.main.datastructures.tree.BinarySearchTree;
import igorilin13.com.github.main.datastructures.tree.OrderedStatisticsTree;
import igorilin13.com.github.main.datastructures.tree.TreeNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderedStatisticsTreeTest extends RedBlackTreeTest {
    @Override
    BinarySearchTree<Integer> createTree() {
        return new OrderedStatisticsTree<>();
    }

    @Override
    boolean checkAdditionalProperties(TreeNode<Integer> root) {
        return super.checkAdditionalProperties(root)
                && checkSizeProperty((OrderedStatisticsTree<Integer>.OrderedStatisticsTreeNode<Integer>) root);
    }

    private boolean checkSizeProperty(OrderedStatisticsTree<Integer>
                                              .OrderedStatisticsTreeNode<Integer> node) {
        return node == null || ((OrderedStatisticsTree<Integer>) tree).calcSize(node) == node.getSize()
                && checkSizeProperty(node.getLeft())
                && checkSizeProperty(node.getRight());
    }

    @Test
    public void testSelect() {
        for (int i = 0; i <= MAX_KEY; i++) {
            assertEquals(keys.get(i), ((OrderedStatisticsTree<Integer>) tree).select(i + 1));
        }
    }
}
