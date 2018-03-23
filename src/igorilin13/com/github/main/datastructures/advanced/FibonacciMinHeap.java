package igorilin13.com.github.main.datastructures.advanced;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FibonacciMinHeap<K extends Comparable<K>> {
    private Node<K> min;
    private int size;

    public int size() {
        return size;
    }

    public void insert(K key) {
        Node<K> newNode = new Node<>(key);
        if (min == null) {
            min = newNode;
            min.right = min.left = min;
        } else {
            min.insertSibling(newNode);
        }
        if (min != newNode && key.compareTo(min.key) < 0) {
            min = newNode;
        }
        size++;
    }

    @Nullable
    public K min() {
        return min != null ? min.key : null;
    }

    @Nullable
    public K extractMin() {
        Node<K> cachedMin = min;
        if (cachedMin != null) {
            if (cachedMin.childrenSize > 0) {
                List<Node<K>> children = copyList(cachedMin.childrenHead);
                for (Node<K> child : children) {
                    min.insertSibling(child);
                    child.parent = null;
                }
            }
            min.unlinkNode(cachedMin);
            if (cachedMin == cachedMin.right) {
                min = null;
            } else {
                min = cachedMin.right;
                consolidate();
            }
            size--;
        }
        return cachedMin != null ? cachedMin.key : null;
    }

    private void consolidate() {
        Map<Integer, Node<K>> nodesByDegree = new HashMap<>();
        List<Node<K>> rootList = copyList(min);
        for (Node<K> currentRootNode : rootList) {
            int currentDegree = currentRootNode.childrenSize;
            while (nodesByDegree.containsKey(currentDegree)) {
                Node<K> conflictingNode = nodesByDegree.get(currentDegree);
                if (currentRootNode.key.compareTo(conflictingNode.key) > 0) {
                    Node<K> swap = currentRootNode;
                    currentRootNode = conflictingNode;
                    conflictingNode = swap;
                }
                link(conflictingNode, currentRootNode);
                nodesByDegree.remove(currentDegree);
                currentDegree++;
            }
            nodesByDegree.put(currentDegree, currentRootNode);
        }

        min = null;
        for (Node<K> node : nodesByDegree.values()) {
            if (min == null) {
                min = node;
                min.left = min.right = min;
            } else {
                min.insertSibling(node);
                if (node.key.compareTo(min.key) < 0) {
                    min = node;
                }
            }
        }
    }

    private List<Node<K>> copyList(Node<K> listHead) {
        List<Node<K>> res = new ArrayList<>();
        Node<K> currentNode = listHead;
        do {
            res.add(currentNode);
            currentNode = currentNode.left;
        } while (currentNode != listHead);
        return res;
    }

    private void link(Node<K> makeChild, Node<K> makeParent) {
        min.unlinkNode(makeChild);
        makeParent.insertChild(makeChild);
        makeChild.mark = false;
    }

    public void union(FibonacciMinHeap<K> other) {
        if (other.min == null) {
            return;
        }
        if (min == null) {
            min = other.min;
            size = other.size;
            return;
        }

        Node<K> otherMin = other.min;
        Node<K> ourMinRight = min.right;
        Node<K> otherMinLeft = otherMin.left;

        min.right = otherMin;
        otherMin.left = min;
        ourMinRight.left = otherMinLeft;
        otherMinLeft.right = ourMinRight;

        if (min.key.compareTo(otherMin.key) < 0) {
            min = otherMin;
        }
        size += other.size;
    }

    public void delete(K key) {
        Node<K> currentNode = search(key);
        while (currentNode != null) {
            makeNodeMin(currentNode);
            extractMin();
            currentNode = search(key);
        }
    }

    public void decreaseKey(K oldKey, K newKey) {
        decreaseKey(search(oldKey), newKey);
    }

    private void makeNodeMin(Node<K> node) {
        decreaseKey(node, null);
    }

    private void decreaseKey(@Nullable Node<K> node, @Nullable K newKey) {
        if (node == null) {
            return;
        }
        if (newKey != null && newKey.compareTo(node.key) >= 0) {
            throw new IllegalArgumentException("New key > old key");
        }
        node.key = newKey;
        Node<K> parent = node.parent;
        if (parent != null && (newKey == null || newKey.compareTo(parent.key) < 0)) {
            cut(node, parent);
            cascadingCut(parent);
        }
        if (newKey == null || newKey.compareTo(min.key) < 0) {
            min = node;
        }
    }

    private void cut(Node<K> child, Node<K> parent) {
        parent.removeChild(child);
        min.insertSibling(child);
        child.parent = null;
        child.mark = false;
    }

    private void cascadingCut(Node<K> node) {
        Node<K> parent = node.parent;
        if (parent != null) {
            if (!node.mark) {
                node.mark = true;
            } else {
                cut(node, parent);
                cascadingCut(parent);
            }
        }
    }

    public boolean contains(K key) {
        return search(key) != null;
    }

    private Node<K> search(K key) {
        return search(min, key);
    }

    @Nullable
    private Node<K> search(@Nullable Node<K> currentListHead, K key) {
        if (currentListHead == null) {
            return null;
        }

        Node<K> currentNode = currentListHead;
        do {
            if (currentNode.key.equals(key)) {
                return currentNode;
            }

            if (currentNode.childrenHead != null) {
                Node<K> searchChildren = search(currentNode.childrenHead, key);
                if (searchChildren != null) {
                    return searchChildren;
                }
            }

            currentNode = currentNode.left;
        } while (currentNode != currentListHead);

        return null;
    }

    private static class Node<K extends Comparable<K>> {
        private Node<K> parent;
        private Node<K> childrenHead;
        private Node<K> left;
        private Node<K> right;
        private K key;
        private int childrenSize;
        private boolean mark; //indicates whether this node has lost a child since the last time it was made the child of another node.

        private Node(K key) {
            this.key = key;
        }

        private void insertChild(Node<K> newChild) {
            newChild.parent = this;
            if (childrenHead != null) {
                childrenHead.insertSibling(newChild);
            } else {
                childrenHead = newChild;
                childrenHead.left = childrenHead.right = childrenHead;
            }
            childrenSize++;
        }

        private void insertSibling(Node<K> sibling) {
            sibling.right = this.right;
            sibling.left = this;
            sibling.right.left = sibling;
            this.right = sibling;
        }

        private void removeChild(Node<K> delChild) {
            if (childrenHead == childrenHead.right) {
                childrenHead = null;
            } else {
                unlinkNode(delChild);
                if (delChild == childrenHead) {
                    childrenHead = (childrenHead.left != null) ? childrenHead.left : childrenHead.right;
                }
            }
            childrenSize--;
        }

        private void unlinkNode(Node<K> z) {
            z.right.left = z.left;
            z.left.right = z.right;
        }
    }
}
