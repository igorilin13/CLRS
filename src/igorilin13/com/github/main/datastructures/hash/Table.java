package igorilin13.com.github.main.datastructures.hash;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Table<T> {
    void insert(@NotNull T value);

    void delete(@NotNull T value);

    @Nullable
    T get(int key);

    boolean contains(@NotNull T value);
}
