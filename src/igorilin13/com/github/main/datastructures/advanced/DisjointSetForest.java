package igorilin13.com.github.main.datastructures.advanced;

import java.util.HashMap;
import java.util.Map;

public class DisjointSetForest<T> {
    private final Map<T, Node<T>> nodes = new HashMap<>();

    public void makeSet(T value) {
        Node<T> node = new Node<>();
        node.parent = node;
        node.rank = 0;
        nodes.put(value, node);
    }

    public void union(T firstVal, T secondVal) {
        Node<T> first = nodes.get(firstVal);
        Node<T> second = nodes.get(secondVal);
        link(findSet(first), findSet(second));
    }

    private void link(Node<T> first, Node<T> second) {
        if (first.rank > second.rank) {
            second.parent = first;
        } else {
            first.parent = second;
            if (first.rank == second.rank) {
                second.rank++;
            }
        }
    }

    public Node<T> findSet(T value) {
        return findSet(nodes.get(value));
    }

    private Node<T> findSet(Node<T> node) {
        if (node != node.parent) {
            node.parent = findSet(node.parent);
        }
        return node.parent;
    }

    static class Node<T> {
        private Node<T> parent;
        private int rank;
    }
}
