package igorilin13.com.github.main.datastructures;

import org.jetbrains.annotations.Nullable;

public class LinkedList<K> {
    protected Node<K> head;

    @Nullable
    public Node<K> search(K key) {
        Node<K> current = head;
        while (current != null && !current.key.equals(key)) {
            current = current.next;
        }
        return current;
    }

    public void insert(K key) {
        Node<K> insertNode = new Node<>(key);
        insertNode.next = head;
        if (head != null) {
            head.prev = insertNode;
        }
        head = insertNode;
    }

    public void delete(K key) {
        Node<K> deleteNode = search(key);
        if (deleteNode == null) {
            return;
        }
        if (deleteNode.prev != null) {
            deleteNode.prev.next = deleteNode.next;
        } else {
            head = deleteNode.next;
        }
        if (deleteNode.next != null) {
            deleteNode.next.prev = deleteNode.prev;
        }
    }

    public static class Node<K> {
        private final K key;
        private Node<K> prev;
        private Node<K> next;

        private Node(K key) {
            this.key = key;
        }

        public K getKey() {
            return key;
        }

        public Node<K> getNext() {
            return next;
        }
    }
}
