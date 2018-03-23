package igorilin13.com.github.main.datastructures.tree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class BinarySearchTree<K extends Comparable<K>> {
    TreeNode<K> root;
    int size;

    public BinarySearchTree() {
    }

    public BinarySearchTree(Collection<K> keys) {
        for (K key : keys) {
            insert(key);
        }
    }

    public TreeNode<K> getRoot() {
        return root;
    }

    public void insert(K key) {
        TreeNode<K> insertNode = createNode(key);
        TreeNode<K> parent = null;
        TreeNode<K> current = root;
        while (current != null) {
            parent = current;
            current = key.compareTo(current.getKey()) < 0 ? current.getLeft() : current.getRight();
        }
        insertNode.setParent(parent);
        if (parent == null) {
            root = insertNode;
        } else if (key.compareTo(parent.getKey()) < 0) {
            parent.setLeft(insertNode);
        } else {
            parent.setRight(insertNode);
        }
        insertFixup(insertNode);
        size++;
    }

    TreeNode<K> createNode(K key) {
        return new TreeNode<>(key);
    }

    void insertFixup(TreeNode<K> node) {
    }

    public void deleteAll(K key) {
        while (delete(key)) {
        }
    }

    public boolean delete(K key) {
        TreeNode<K> deleteNode = iterativeSearch(key);
        return deleteNode != null && delete(deleteNode);
    }

    boolean delete(@NotNull TreeNode<K> deleteNode) {
        if (deleteNode.getLeft() == null) {
            transplant(deleteNode, deleteNode.getRight());
        } else if (deleteNode.getRight() == null) {
            transplant(deleteNode, deleteNode.getLeft());
        } else {
            TreeNode<K> moveNode = minNode(deleteNode.getRight());
            if (moveNode.getParent() != deleteNode) {
                transplant(moveNode, moveNode.getRight());
                moveNode.setRight(deleteNode.getRight());
                if (moveNode.getRight() != null) {
                    moveNode.getRight().setParent(moveNode);
                }
            }
            transplant(deleteNode, moveNode);
            moveNode.setLeft(deleteNode.getLeft());
            if (moveNode.getLeft() != null) {
                moveNode.getLeft().setParent(moveNode);
            }
        }
        size--;
        return true;
    }

    void transplant(@NotNull TreeNode<K> fromNode, @Nullable TreeNode<K> toNode) {
        if (fromNode.getParent() == null) {
            root = toNode;
        } else if (fromNode == fromNode.getParent().getLeft()) {
            fromNode.getParent().setLeft(toNode);
        } else {
            fromNode.getParent().setRight(toNode);
        }
        if (toNode != null) {
            toNode.setParent(fromNode.getParent());
        }
    }

    public TreeWalkResult<K> inOrderWalk() {
        return new TreeWalkResult<>(inOrderWalkList(root));
    }

    private List<TreeNode<K>> inOrderWalkList(TreeNode<K> node) {
        List<TreeNode<K>> res = new ArrayList<>();
        if (node != null) {
            res.addAll(inOrderWalkList(node.getLeft()));
            res.add(node);
            res.addAll(inOrderWalkList(node.getRight()));
        }
        return res;
    }

    @Nullable
    public TreeNode<K> recursiveSearch(K key) {
        return recursiveSearch(root, key);
    }

    @Nullable
    private TreeNode<K> recursiveSearch(TreeNode<K> node, K key) {
        if (node == null || key.equals(node.getKey())) {
            return node;
        }
        if (key.compareTo(node.getKey()) < 0) {
            return recursiveSearch(node.getLeft(), key);
        } else {
            return recursiveSearch(node.getRight(), key);
        }
    }

    @Nullable
    public TreeNode<K> iterativeSearch(K key) {
        return iterativeSearch(root, key);
    }

    @Nullable
    private TreeNode<K> iterativeSearch(TreeNode<K> current, K key) {
        while (current != null && !key.equals(current.getKey())) {
            current = key.compareTo(current.getKey()) < 0 ? current.getLeft() : current.getRight();
        }
        return current;
    }

    @Nullable
    public K pollMinKey() {
        if (isEmpty()) {
            return null;
        }
        TreeNode<K> minNode = minNode(root);
        delete(minNode);
        return minNode.getKey();
    }

    @Nullable
    public K minKey() {
        if (root == null) {
            return null;
        }
        return minNode(root).getKey();
    }

    @NotNull
    TreeNode<K> minNode(@NotNull TreeNode<K> current) {
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    @Nullable
    public K pollMaxKey() {
        if (isEmpty()) {
            return null;
        }
        TreeNode<K> maxNode = maxNode(root);
        delete(maxNode);
        return maxNode.getKey();
    }

    @Nullable
    public K maxKey() {
        if (root == null) {
            return null;
        }
        return maxNode(root).getKey();
    }

    private TreeNode<K> maxNode(@NotNull TreeNode<K> current) {
        while (current.getRight() != null) {
            current = current.getRight();
        }
        return current;
    }

    @Nullable
    public K prevKey(K key) {
        return findDistinctKeyInOrder(key, this::predecessorNode);
    }

    private K findDistinctKeyInOrder(K key, Function<TreeNode<K>, TreeNode<K>> findNextNode) {
        TreeNode<K> current = iterativeSearch(root, key);
        K res = key;
        do {
            current = findNextNode.apply(current);
            if (current != null) {
                res = current.getKey();
            }
        } while (current != null && current.getKey().equals(key));
        return !res.equals(key) ? res : null;
    }

    @Nullable
    private TreeNode<K> predecessorNode(TreeNode<K> from) {
        if (from == null) {
            return null;
        }
        if (from.getLeft() != null) {
            return maxNode(from.getLeft());
        }
        TreeNode<K> current = from.getParent();
        while (current != null && from == current.getLeft()) {
            from = current;
            current = current.getParent();
        }
        return current;
    }

    @Nullable
    public K nextKey(K key) {
        return findDistinctKeyInOrder(key, this::successorNode);
    }

    @Nullable
    private TreeNode<K> successorNode(TreeNode<K> from) {
        if (from == null) {
            return null;
        }
        if (from.getRight() != null) {
            return minNode(from.getRight());
        }
        TreeNode<K> current = from.getParent();
        while (current != null && from == current.getRight()) {
            from = current;
            current = current.getParent();
        }
        return current;
    }

    public int count(K key) {
        return count(root, key);
    }

    private int count(@Nullable TreeNode<K> node, K key) {
        if (node == null) {
            return 0;
        }

        TreeNode<K> match = iterativeSearch(node, key);
        if (match == null) {
            return 0;
        }

        return count(match.getLeft(), key) + count(match.getRight(), key) + 1;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }
}
