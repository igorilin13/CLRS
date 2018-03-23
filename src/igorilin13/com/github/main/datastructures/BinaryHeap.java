package igorilin13.com.github.main.datastructures;

import java.util.Collections;
import java.util.List;

public class BinaryHeap<T extends Comparable<T>> {
    private final Type type;
    private final List<T> heap;
    private int size;

    BinaryHeap(List<T> representation, Type type) {
        this.type = type;
        heap = representation;
        build();
    }

    private void build() {
        size = heap.size();
        for (int i = size / 2 - 1; i >= 0; i--) {
            heapify(i);
        }
    }

    public void heapify(int i) {
        int leftChild = leftChild(i);
        int rightChild = rightChild(i);
        int largest;
        if (leftChild < size && isPropertyViolated(i, leftChild)) {
            largest = leftChild;
        } else {
            largest = i;
        }
        if (rightChild < size && isPropertyViolated(largest, rightChild)) {
            largest = rightChild;
        }
        if (largest != i) {
            Collections.swap(heap, i, largest);
            heapify(largest);
        }
    }

    public static int parent(int i) {
        return (i - 1) / 2;
    }

    private static int leftChild(int i) {
        return 2 * i + 1;
    }

    private static int rightChild(int i) {
        return 2 * (i + 1);
    }

    boolean isPropertyViolated(int parent, int child) {
        if (type == Type.MIN) {
            return heap.get(parent).compareTo(heap.get(child)) > 0;
        } else {
            return heap.get(parent).compareTo(heap.get(child)) < 0;
        }
    }

    public void decreaseSize() {
        size = Math.max(size - 1, 0);
    }

    public void increaseSize() {
        size++;
    }

    public static <T extends Comparable<T>> BinaryHeap<T> createMaxHeap(List<T> representation) {
        return new BinaryHeap<>(representation, Type.MAX);
    }

    public static <T extends Comparable<T>> BinaryHeap<T> createMinHeap(List<T> representation) {
        return new BinaryHeap<>(representation, Type.MIN);
    }

    public boolean isEmpty() {
        return size <= 0;
    }

    public int size() {
        return size;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        MIN,
        MAX
    }
}
