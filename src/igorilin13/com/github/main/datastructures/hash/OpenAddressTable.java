package igorilin13.com.github.main.datastructures.hash;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public class OpenAddressTable<T> implements Table<T> {
    private final BiFunction<Integer, Integer, Integer> function;
    private final ValueHolder<T>[] data;

    public OpenAddressTable(Type type) {
        int universeSize = 349;
        switch (type) {
            case LINEAR:
                function = FunctionsProvider.linearProbing(universeSize);
                break;
            case QUADRATIC:
                function = FunctionsProvider.quadraticProbing(universeSize);
                break;
            case DOUBLE:
                function = FunctionsProvider.doubleProbing(universeSize);
                break;
            default:
                throw new IllegalArgumentException("Unknown function type");
        }
        data = (ValueHolder<T>[]) new ValueHolder[universeSize];
        for (int i = 0; i < universeSize; i++) {
            data[i] = new ValueHolder<>();
        }
    }

    @Override
    public void insert(@NotNull T value) {
        int key = value.hashCode();
        for (int i = 0; i < data.length; i++) {
            int j = function.apply(key, i);
            if (data[j].state != ValueHolder.State.OCCUPIED) {
                data[j].set(value);
                return;
            }
        }
        throw new OutOfMemoryError("Table overflow");
    }

    @Override
    public void delete(@NotNull T value) {
        int index = find(value.hashCode());
        if (index >= 0) {
            data[index].delete();
        }
    }

    @Override
    public boolean contains(@NotNull T value) {
        return get(value.hashCode()) != null;
    }

    @Nullable
    @Override
    public T get(int key) {
        int res = find(key);
        return res >= 0 ? data[res].value : null;
    }


    private int find(int key) {
        for (int i = 0; i < data.length; i++) {
            int j = function.apply(key, i);
            if (data[j].state == ValueHolder.State.EMPTY) {
                return -1;
            }
            if (data[j].state != ValueHolder.State.DELETED && data[j].value.hashCode() == key) {
                return j;
            }
        }
        return -1;
    }

    private static class ValueHolder<T> {
        private T value;
        private State state = State.EMPTY;

        private void set(T value) {
            this.value = value;
            if (value != null) {
                state = State.OCCUPIED;
            }
        }

        private void delete() {
            value = null;
            state = State.DELETED;
        }

        private enum State {
            EMPTY,
            DELETED,
            OCCUPIED
        }
    }

    public enum Type {
        LINEAR,
        QUADRATIC,
        DOUBLE
    }
}
