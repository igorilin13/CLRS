package igorilin13.com.github.main.datastructures;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static igorilin13.com.github.main.datastructures.BinaryHeap.parent;

public class PriorityQueue<T extends Comparable<T>> {
    private final BinaryHeap.Type type;
    private final BinaryHeap<T> heap;
    private final List<T> keys;
    private final T newKeyPlaceholder = null;

    private PriorityQueue(BinaryHeap.Type type) {
        this.type = type;
        keys = new ArrayList<>();
        heap = new BinaryHeap<>(keys, type);
    }

    @Nullable
    public T peek() {
        return heap.isEmpty() ? null : keys.get(0);
    }

    @Nullable
    public T poll() {
        if (heap.isEmpty()) {
            return null;
        }
        T res = keys.get(0);
        keys.set(0, keys.get(heap.size() - 1));
        heap.decreaseSize();
        heap.heapify(0);
        return res;
    }

    public void insert(@NotNull T key) {
        int size = heap.size();
        if (keys.size() > size) {
            keys.set(size, newKeyPlaceholder);
        } else {
            keys.add(newKeyPlaceholder);
        }
        heap.increaseSize();
        increasePriority(size, key);
    }

    public void increasePriority(int i, @NotNull T newKey) {
        T prevKey = keys.get(i);
        if (newKey.equals(prevKey)) {
            return;
        }
        throwIfDecreasesPriority(prevKey, newKey);
        keys.set(i, newKey);
        int parent = parent(i);
        while (i > 0 && heap.isPropertyViolated(parent, i)) {
            Collections.swap(keys, i, parent);
            i = parent;
            parent = parent(i);
        }
    }

    private void throwIfDecreasesPriority(T prevKey, T newKey) {
        if (prevKey == newKeyPlaceholder) {
            return;
        }
        int compared = prevKey.compareTo(newKey);
        if (type == BinaryHeap.Type.MAX && compared > 0 || type == BinaryHeap.Type.MIN && compared < 0) {
            throw new IllegalArgumentException("New key decreases priority");
        }
    }

    public int size() {
        return heap.size();
    }

    public static <T extends Comparable<T>> PriorityQueue<T> createMax() {
        return new PriorityQueue<>(BinaryHeap.Type.MAX);
    }

    public static <T extends Comparable<T>> PriorityQueue<T> createMin() {
        return new PriorityQueue<>(BinaryHeap.Type.MIN);
    }
}
