package igorilin13.com.github.main.datastructures.hash;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DirectAddressTable<T> implements Table<T> {
    private final T[] data;

    /**
     * Assumes each element has a key drawn from U = {0, 1, ..., universeSize - 1},
     * and no two elements have the same key
     */
    public DirectAddressTable(int universeSize) {
        data = (T[]) new Object[universeSize];
    }

    @Nullable
    @Override
    public T get(int key) {
        return data[key];
    }

    @Override
    public void insert(@NotNull T value) {
        data[value.hashCode()] = value;
    }

    @Override
    public boolean contains(@NotNull T value) {
        return get(value.hashCode()) != null;
    }

    @Override
    public void delete(@NotNull T value) {
        data[value.hashCode()] = null;
    }
}
