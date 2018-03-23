package igorilin13.com.github.main.datastructures.tree;

import org.jetbrains.annotations.NotNull;

public class OrderedStatisticsTree<K extends Comparable<K>> extends RedBlackTree<K> {

    @Override
    TreeNode<K> createNode(K key) {
        return new OrderedStatisticsTreeNode<>(key);
    }

    @Override
    void insertFixup(TreeNode<K> node) {
        super.insertFixup(node);
        recalcSizes((OrderedStatisticsTreeNode<K>) root);
    }

    @Override
    boolean delete(@NotNull TreeNode<K> deleteNode) {
        boolean res = super.delete(deleteNode);
        if (res) {
            recalcSizes((OrderedStatisticsTreeNode<K>) root);
        }
        return res;
    }

    private int recalcSizes(OrderedStatisticsTreeNode<K> node) {
        if (node == null) {
            return 0;
        }
        int size = recalcSizes(node.getLeft()) + recalcSizes(node.getRight()) + 1;
        node.setSize(size);
        return size;
    }

    public int calcSize(@NotNull OrderedStatisticsTreeNode<K> node) {
        return sizeOrZero(node.getLeft()) + sizeOrZero(node.getRight()) + 1;
    }

    private int sizeOrZero(OrderedStatisticsTreeNode<K> node) {
        return node != null ? node.getSize() : 0;
    }

    public K select(int i) {
        return select((OrderedStatisticsTreeNode<K>) root, i).getKey();
    }

    private OrderedStatisticsTreeNode<K> select(OrderedStatisticsTreeNode<K> x, int i) {
        int r = sizeOrZero(x.getLeft()) + 1;
        if (i == r) {
            return x;
        } else if (i < r) {
            return select(x.getLeft(), i);
        } else {
            return select(x.getRight(), i - r);
        }
    }

    public int calcRank(OrderedStatisticsTreeNode<K> x) {
        int result = sizeOrZero(x.getLeft()) + 1;
        OrderedStatisticsTreeNode<K> current = x;
        while (current != root) {
            if (current == current.getParent().getRight()) {
                result += sizeOrZero(current.getParent().getLeft()) + 1;
            }
            current = current.getParent();
        }
        return result;
    }

    public class OrderedStatisticsTreeNode<T extends Comparable<T>> extends RedBlackTreeNode<T> {
        private int size;

        OrderedStatisticsTreeNode(T key) {
            super(key);
        }

        void setSize(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }

        @Override
        public OrderedStatisticsTreeNode<T> getParent() {
            return (OrderedStatisticsTreeNode<T>) super.getParent();
        }

        @Override
        public OrderedStatisticsTreeNode<T> getLeft() {
            return (OrderedStatisticsTreeNode<T>) super.getLeft();
        }

        @Override
        public OrderedStatisticsTreeNode<T> getRight() {
            return (OrderedStatisticsTreeNode<T>) super.getRight();
        }
    }
}
