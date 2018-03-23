package igorilin13.com.github.main.datastructures;

import igorilin13.com.github.main.util.ArrayUtils;
import org.jetbrains.annotations.Nullable;

public class Queue<E> {
    private E[] queue;
    private int capacity;
    private int head;
    private int tail;

    public Queue() {
        capacity = 256;
        head = tail = 0;
        queue = (E[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return head >= tail;
    }

    public void enqueue(E element) {
        queue[tail] = element;
        tail++;
        if (tail >= capacity) {
            capacity *= 2;
            queue = ArrayUtils.extendCopy(queue, capacity);
        }
    }

    @Nullable
    public E dequeue() {
        if (isEmpty()) {
            return null;
        }
        E element = queue[head];
        head++;
        return element;
    }
}
