package igorilin13.com.github.main.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TupleN<E> {
    private List<E> elements;

    @SafeVarargs
    public TupleN(E... elements) {
        this.elements = new ArrayList<>(Arrays.asList(elements));
    }

    public void set(int index, E value) {
        elements.set(index, value);
    }

    public E get(int index) {
        return elements.get(index);
    }

    public int size() {
        return elements.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TupleN<?> tupleN = (TupleN<?>) o;

        return elements.equals(tupleN.elements);
    }

    @Override
    public int hashCode() {
        return elements.hashCode();
    }

    @Override
    public String toString() {
        return elements.toString();
    }
}
