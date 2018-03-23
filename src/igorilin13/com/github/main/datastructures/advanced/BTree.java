package igorilin13.com.github.main.datastructures.advanced;

import igorilin13.com.github.main.util.ListUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BTree<K extends Comparable<K>> {
    private final int degree;
    public Node<K> root;

    public BTree(int degree) {
        if (degree < 2) {
            throw new IllegalArgumentException("Degree should be >= 2");
        }

        this.degree = degree;
        Node<K> root = new Node<>();
        diskWrite(root);
        this.root = root;
    }

    private void diskWrite(Node<K> node) {
    }

    private void diskRead(Node<K> node) {
    }

    public boolean contains(K key) {
        return search(key) != null;
    }

    @Nullable
    private Node<K> search(K key) {
        return search(root, key);
    }

    private Node<K> search(Node<K> current, K key) {
        int i = 1;
        while (i <= current.size && key.compareTo(current.key(i - 1)) > 0) {
            i++;
        }
        if (i <= current.size && key.equals(current.key(i - 1))) {
            return current;
        } else if (current.leaf) {
            return null;
        } else {
            diskRead(current.child(i - 1));
            return search(current.child(i - 1), key);
        }
    }

    public void insert(K key) {
        Node<K> cachedRoot = this.root;
        if (cachedRoot.size == 2 * degree - 1) {
            Node<K> newRoot = new Node<>();
            this.root = newRoot;
            newRoot.leaf = false;
            newRoot.children.add(cachedRoot);
            splitChild(newRoot, 0);
        }
        insertNonFull(this.root, key);
    }

    private void insertNonFull(Node<K> currentNode, K key) {
        int i = currentNode.size - 1;
        if (currentNode.leaf) {
            while (i >= 0 && key.compareTo(currentNode.key(i)) < 0) {
                currentNode.insertKey(i + 1, currentNode.key(i));
                i--;
            }
            currentNode.insertKey(i + 1, key);
            currentNode.size++;
            diskWrite(currentNode);
        } else {
            while (i >= 0 && key.compareTo(currentNode.key(i)) < 0) {
                i--;
            }
            i++;
            diskRead(currentNode.child(i));
            if (currentNode.child(i).size == 2 * degree - 1) {
                splitChild(currentNode, i);
                if (key.compareTo(currentNode.key(i)) > 0) {
                    i++;
                }
            }
            insertNonFull(currentNode.child(i), key);
        }
    }

    private void splitChild(Node<K> nonFullParent, int fullChildInd) {
        Node<K> rightSplitNode = new Node<>();
        Node<K> fullChild = nonFullParent.child(fullChildInd);
        rightSplitNode.leaf = fullChild.leaf;
        rightSplitNode.size = degree - 1;
        for (int j = 1; j <= degree - 1; j++) {
            rightSplitNode.insertKey(j - 1, fullChild.key(j + degree - 1));
        }
        if (!fullChild.leaf) {
            for (int j = 1; j <= degree; j++) {
                rightSplitNode.insertChild(j - 1, fullChild.child(j + degree - 1));
            }
        }
        if (nonFullParent.size == 0) {
            nonFullParent.insertChild(1, rightSplitNode);
        } else {
            for (int j = nonFullParent.size + 1; j > fullChildInd + 1; j--) {
                nonFullParent.insertChild(j, nonFullParent.child(j - 1));
            }
            nonFullParent.insertChild(fullChildInd + 1, rightSplitNode);
            for (int j = nonFullParent.size; j >= fullChildInd + 1; j--) {
                nonFullParent.insertKey(j, nonFullParent.key(j - 1));
            }
        }
        nonFullParent.insertKey(fullChildInd, fullChild.key(degree - 1));
        nonFullParent.size++;
        fullChild.setSize(degree - 1);
        diskWrite(fullChild);
        diskWrite(rightSplitNode);
        diskWrite(nonFullParent);
    }

    public String inOrderWalk() {
        return inOrderWalk(root);
    }

    private String inOrderWalk(Node<K> node) {
        StringBuilder res = new StringBuilder();
        if (node != null) {
            for (int i = 0; i < node.size; i++) {
                if (!node.leaf) {
                    res.append(inOrderWalk(node.child(i)));
                }
                res.append(node.key(i));
            }
            if (!node.leaf) {
                res.append(inOrderWalk(node.child(node.size)));
            }
        }
        return res.toString();
    }

    //ignoring diskWrite/diskRead
    public void delete(K key) {
        delete(root, key);
        if (root.size == 0 && root.children.size() == 1) {
            root = root.child(0);
        }
    }

    private void delete(Node<K> currentNode, K key) {
        int keyIndex = currentNode.keys.indexOf(key);
        if (keyIndex >= 0) {
            if (currentNode.leaf) {
                //case 1
                deleteKeyFromLeaf(currentNode, keyIndex);
            } else {
                //case 2
                deleteKeyFromNotLeaf(currentNode, key, keyIndex);
            }
        } else {
            descendDeletion(currentNode, key);
        }
    }

    private void deleteKeyFromLeaf(Node<K> node, int keyIndex) {
        node.keys.remove(keyIndex);
        node.size--;
    }

    private void deleteKeyFromNotLeaf(Node<K> node, K key, int keyIndex) {
        Node<K> leftChild = node.child(keyIndex);
        if (leftChild.size >= degree) {
            //case 2.a
            Node<K> current = leftChild;
            while (!current.leaf) {
                current = leftChild.child(leftChild.size);
            }
            replaceWithChildKey(node, keyIndex, leftChild, current.key(current.size - 1));
            return;
        }

        Node<K> rightChild = node.child(keyIndex + 1);
        if (rightChild.size >= degree) {
            //case 2.b
            Node<K> current = rightChild;
            while (!current.leaf) {
                current = rightChild.child(0);
            }
            replaceWithChildKey(node, keyIndex, rightChild, current.key(0));
            return;
        }

        //case 2.c
        node.keys.remove(keyIndex);
        node.size--;
        leftChild.keys.add(key);
        leftChild.keys.addAll(rightChild.keys);
        if (!leftChild.leaf) {
            leftChild.children.addAll(rightChild.children);
        }
        leftChild.size += rightChild.size + 1;
        node.children.remove(keyIndex + 1);
        delete(leftChild, key);
    }

    private void replaceWithChildKey(Node<K> parent, int parentKeyIndex, Node<K> child, K childKey) {
        parent.keys.set(parentKeyIndex, childKey);
        delete(child, childKey);
    }

    private void descendDeletion(Node<K> currentNode, K key) {
        int descendIndex = 0;
        while (descendIndex < currentNode.size && key.compareTo(currentNode.key(descendIndex)) > 0) {
            descendIndex++;
        }

        Node<K> descendNode = currentNode.child(descendIndex);
        if (descendNode.size >= degree) {
            //case 3
            delete(descendNode, key);
            return;
        }

        Node<K> leftSibling = descendIndex > 0 ? currentNode.child(descendIndex - 1) : null;
        Node<K> rightSibling = descendIndex < currentNode.size ? currentNode.child(descendIndex + 1) : null;
        if ((leftSibling != null && leftSibling.size >= degree)) {
            //case 3a.a
            extendDescendNode(currentNode, currentNode.key(descendIndex - 1), descendIndex - 1,
                    descendNode, true, leftSibling, leftSibling.size - 1);
        } else if (rightSibling != null && rightSibling.size >= degree) {
            //case 3a.b
            extendDescendNode(currentNode, currentNode.key(descendIndex), descendIndex,
                    descendNode, false, rightSibling, 0);
        } else {
            //case 3b
            if (leftSibling != null) {
                mergeSiblings(currentNode, currentNode.key(descendIndex - 1), leftSibling, descendNode);
                descendNode = leftSibling;
            } else if (rightSibling != null) {
                mergeSiblings(currentNode, currentNode.key(descendIndex), descendNode, rightSibling);
            }
        }
        delete(descendNode, key);
    }

    private void extendDescendNode(Node<K> parent, K parentKey, int parentKeyIndex,
                                   Node<K> descendNode,
                                   boolean leftSibling, Node<K> siblingNode, int moveupKeyIndex) {
        parent.keys.set(parentKeyIndex, siblingNode.key(moveupKeyIndex));
        siblingNode.keys.remove(moveupKeyIndex);
        Node<K> moveChild = !siblingNode.leaf ? siblingNode.child(leftSibling ? moveupKeyIndex + 1 : moveupKeyIndex) : null;
        if (!siblingNode.leaf) {
            siblingNode.children.remove(moveChild);
        }
        siblingNode.size--;

        if (leftSibling) {
            if (!descendNode.leaf) {
                descendNode.insertChild(descendNode.size + 1, descendNode.child(descendNode.size));
            }
            for (int i = descendNode.size - 1; i >= 0; i--) {
                descendNode.insertKey(i + 1, descendNode.key(i));
                if (!descendNode.leaf) {
                    descendNode.insertChild(i + 1, descendNode.child(i));
                }
            }
            if (!descendNode.leaf) {
                descendNode.children.set(0, moveChild);
            }
            descendNode.keys.set(0, parentKey);
        } else {
            descendNode.keys.add(parentKey);
            if (!descendNode.leaf) {
                descendNode.children.add(moveChild);
            }
        }
        descendNode.size++;
    }

    private void mergeSiblings(Node<K> parent, K parentKey, Node<K> leftSibling, Node<K> rightSibling) {
        leftSibling.keys.add(parentKey);
        leftSibling.keys.addAll(rightSibling.keys);
        if (!leftSibling.leaf) {
            leftSibling.children.addAll(rightSibling.children);
        }
        parent.keys.remove(parentKey);
        parent.children.remove(rightSibling);
        parent.size--;
        leftSibling.size += rightSibling.size + 1;
        if (root.size == 0) {
            root = leftSibling;
        }
    }

    public int getDegree() {
        return degree;
    }

    public static class Node<K extends Comparable<K>> {
        private int size;
        private List<K> keys;
        private List<Node<K>> children;
        private boolean leaf;

        private Node() {
            leaf = true;
            size = 0;
            keys = new ArrayList<>();
            children = new ArrayList<>();
        }

        private void setSize(int newSize) {
            if (newSize < keys.size()) {
                for (int i = keys.size() - 1; i >= newSize; i--) {
                    keys.remove(i);
                    if (!leaf) {
                        children.remove(i + 1);
                    }
                }
            }
            size = newSize;
        }

        public K key(int index) {
            return keys.get(index);
        }

        private void insertKey(int insertIndex, K key) {
            ListUtils.extendInsert(keys, insertIndex, key);
        }

        public Node<K> child(int index) {
            return children.get(index);
        }

        private void insertChild(int insertIndex, Node<K> child) {
            ListUtils.extendInsert(children, insertIndex, child);
        }

        public int size() {
            return size;
        }

        public List<K> getKeys() {
            return keys;
        }

        public boolean isLeaf() {
            return leaf;
        }

        public List<Node<K>> getChildren() {
            return children;
        }
    }
}
