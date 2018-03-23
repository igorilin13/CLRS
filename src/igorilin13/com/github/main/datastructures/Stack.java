package igorilin13.com.github.main.datastructures;

import igorilin13.com.github.main.util.ArrayUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Stack<E> {
    private E[] stack;
    private int capacity;
    private int top;

    public Stack() {
        capacity = 256;
        top = -1;
        stack = (E[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return top < 0;
    }

    public void push(E element) {
        top++;
        if (top >= capacity) {
            capacity *= 2;
            stack = ArrayUtils.extendCopy(stack, capacity);
        }
        stack[top] = element;
    }

    @Nullable
    public E pop() {
        if (isEmpty()) {
            return null;
        }
        top--;
        return stack[top + 1];
    }

    public List<E> multiPop(int number) {
        int size = Math.min(number, top + 1);
        List<E> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(pop());
        }
        return result;
    }

    @Nullable
    public E peek() {
        return peekFromTop(0);
    }

    @Nullable
    public E peekFromTop(int offset) {
        if (top < offset) {
            return null;
        }
        return stack[top - offset];
    }

    public int size() {
        return top + 1;
    }

    public List<E> toList() {
        return Arrays.asList(stack).subList(0, top + 1);
    }
}
