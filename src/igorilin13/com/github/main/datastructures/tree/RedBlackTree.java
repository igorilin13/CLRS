package igorilin13.com.github.main.datastructures.tree;

import org.jetbrains.annotations.NotNull;

import static igorilin13.com.github.main.datastructures.tree.RedBlackTree.Color.BLACK;
import static igorilin13.com.github.main.datastructures.tree.RedBlackTree.Color.RED;

public class RedBlackTree<K extends Comparable<K>> extends BinarySearchTree<K> {
    @Override
    TreeNode<K> createNode(K key) {
        return new RedBlackTreeNode<>(key);
    }

    @Override
    void insertFixup(TreeNode<K> node) {
        if (node == null) {
            return;
        }
        RedBlackTreeNode<K> z = (RedBlackTreeNode<K>) node;
        z.setColor(RED);
        while (z.getParent() != null && z.getParent().getColor() == RED) {
            RedBlackTreeNode<K> zParent = z.getParent();
            RedBlackTreeNode<K> zParentParent = zParent.getParent();
            RedBlackTreeNode<K> y;
            if (zParent == zParentParent.getLeft()) {
                y = zParentParent.getRight();
                if (y == null || y.getColor() == BLACK) {
                    if (z == zParent.getRight()) {
                        z = zParent;
                        leftRotate(z);
                        zParent = z.getParent();
                    }
                    rightRotate(zParentParent);
                }
            } else {
                y = zParentParent.getLeft();
                if (y == null || y.getColor() == BLACK) {
                    if (z == zParent.getLeft()) {
                        z = zParent;
                        rightRotate(z);
                        zParent = z.getParent();
                    }
                    leftRotate(zParentParent);
                }
            }
            if (y == null || y.getColor() == RED) {
                z = zParentParent;
                if (y != null) {
                    y.setColor(BLACK);
                }
            }
            zParent.setColor(BLACK);
            zParentParent.setColor(RED);
        }
        ((RedBlackTreeNode<K>) root).setColor(BLACK);
    }

    private void leftRotate(RedBlackTreeNode<K> x) {
        if (x == null || x.getRight() == null) {
            return;
        }
        RedBlackTreeNode<K> y = x.getRight();
        x.setRight(y.getLeft());
        if (y.getLeft() != null) {
            y.getLeft().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == null) {
            root = y;
        } else if (x == x.getParent().getLeft()) {
            x.getParent().setLeft(y);
        } else {
            x.getParent().setRight(y);
        }
        y.setLeft(x);
        x.setParent(y);
    }

    private void rightRotate(RedBlackTreeNode<K> y) {
        if (y == null || y.getLeft() == null) {
            return;
        }
        RedBlackTreeNode<K> x = y.getLeft();
        y.setLeft(x.getRight());
        if (x.getRight() != null) {
            x.getRight().setParent(y);
        }
        x.setParent(y.getParent());

        if (y.getParent() == null) {
            root = x;
        } else if (y == y.getParent().getLeft()) {
            y.getParent().setLeft(x);
        } else {
            y.getParent().setRight(x);
        }
        x.setRight(y);
        y.setParent(x);
    }

    @Override
    boolean delete(@NotNull TreeNode<K> deleteNode) {
        RedBlackTreeNode<K> z = (RedBlackTreeNode<K>) deleteNode;
        RedBlackTreeNode<K> y = z;
        RedBlackTreeNode<K> x;
        RedBlackTreeNode<K> xParent = z.getParent();
        Color yOriginalColor = y.getColor();
        if (z.getLeft() == null) {
            x = z.getRight();
            transplant(z, x);
        } else if (z.getRight() == null) {
            x = z.getLeft();
            transplant(z, x);
        } else {
            y = (RedBlackTreeNode<K>) minNode(z.getRight());
            x = y.getRight();
            yOriginalColor = y.getColor();
            if (y.getParent() != z) {
                xParent = y.getParent();
                transplant(y, y.getRight());
                y.setRight(z.getRight());
                if (y.getRight() != null) {
                    y.getRight().setParent(y);
                }
            } else {
                xParent = y;
            }
            transplant(z, y);
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
            y.setColor(z.getColor());
        }
        if (yOriginalColor == BLACK) {
            deleteFixup(x, xParent);
        }
        size--;
        return true;
    }

    private void deleteFixup(RedBlackTreeNode<K> x, RedBlackTreeNode<K> xParent) {
        while (x != root && (x == null || x.getColor() == BLACK)) {
            if (x == xParent.getLeft()) {
                RedBlackTreeNode<K> w = xParent.getRight();
                if (w.getColor() == RED) {
                    w.setColor(BLACK);
                    xParent.setColor(RED);
                    leftRotate(xParent);
                    w = xParent.getRight();
                }
                RedBlackTreeNode<K> wLeft = w.getLeft();
                RedBlackTreeNode<K> wRight = w.getRight();
                if ((wLeft == null || wLeft.getColor() == BLACK)
                        && (wRight == null || wRight.getColor() == BLACK)) {
                    w.setColor(RED);
                    x = xParent;
                    xParent = x.getParent();
                } else {
                    if ((wLeft != null && wLeft.getColor() == RED) && (wRight == null || wRight.getColor() == BLACK)) {
                        wLeft.setColor(BLACK);
                        w.setColor(RED);
                        rightRotate(w);
                        w = xParent.getRight();
                        wRight = w.getRight();
                    }
                    w.setColor(xParent.getColor());
                    xParent.setColor(BLACK);
                    if (wRight != null) {
                        wRight.setColor(BLACK);
                    }
                    leftRotate(xParent);
                    x = (RedBlackTreeNode<K>) root;
                }
            } else {
                RedBlackTreeNode<K> w = xParent.getLeft();
                if (w.getColor() == RED) {
                    w.setColor(BLACK);
                    xParent.setColor(RED);
                    rightRotate(xParent);
                    w = xParent.getLeft();
                }
                RedBlackTreeNode<K> wLeft = w.getLeft();
                RedBlackTreeNode<K> wRight = w.getRight();
                if ((wLeft == null || wLeft.getColor() == BLACK)
                        && (wRight == null || wRight.getColor() == BLACK)) {
                    w.setColor(RED);
                    x = xParent;
                    xParent = x.getParent();
                } else {
                    if (wRight != null && wRight.getColor() == RED && (wLeft == null || wLeft.getColor() == BLACK)) {
                        wRight.setColor(BLACK);
                        w.setColor(RED);
                        leftRotate(w);
                        w = xParent.getLeft();
                        wLeft = w.getLeft();
                    }
                    w.setColor(xParent.getColor());
                    xParent.setColor(BLACK);
                    if (wLeft != null) {
                        wLeft.setColor(BLACK);
                    }
                    rightRotate(xParent);
                    x = (RedBlackTreeNode<K>) root;
                }
            }
        }
        if (x != null) {
            x.setColor(BLACK);
        }

    }

    public class RedBlackTreeNode<T extends Comparable<T>> extends TreeNode<T> {
        private Color color;

        RedBlackTreeNode(T key) {
            super(key);
            color = BLACK;
        }

        void setColor(Color color) {
            this.color = color;
        }

        @Override
        public RedBlackTreeNode<T> getParent() {
            return (RedBlackTreeNode<T>) super.getParent();
        }

        @Override
        public RedBlackTreeNode<T> getLeft() {
            return (RedBlackTreeNode<T>) super.getLeft();
        }

        @Override
        public RedBlackTreeNode<T> getRight() {
            return (RedBlackTreeNode<T>) super.getRight();
        }

        public Color getColor() {
            return color;
        }
    }

    public enum Color {
        RED,
        BLACK
    }
}
