package igorilin13.com.github.main.datastructures.hash;

import igorilin13.com.github.main.datastructures.LinkedList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class ChainedHashTable<T> implements Table<T> {
    private final Function<Integer, Integer> hashFunction;
    private final Chain<T>[] data;

    public ChainedHashTable(Type type) {
        int universeSize;
        switch (type) {
            case DIVISION:
                universeSize = 349;
                hashFunction = FunctionsProvider.divisionHash(universeSize);
                break;
            case MULTIPLICATION:
                universeSize = 256;
                hashFunction = FunctionsProvider.multiplicationHash(universeSize);
                break;
            default:
                throw new IllegalArgumentException("Unknown hash function type");
        }
        data = (Chain<T>[]) new Chain[universeSize];
    }

    @Override
    public void insert(@NotNull T value) {
        int hash = hashFunction.apply(value.hashCode());
        Chain<T> list = data[hash];
        if (list == null) {
            list = new Chain<>();
            data[hash] = list;
        }
        list.insert(value);
    }

    @Override
    public void delete(@NotNull T value) {
        int hash = hashFunction.apply(value.hashCode());
        Chain<T> list = data[hash];
        if (list != null) {
            list.delete(value);
        }
    }

    @Override
    public boolean contains(@NotNull T value) {
        return get(value.hashCode()) != null;
    }

    @Nullable
    @Override
    public T get(int key) {
        int hash = hashFunction.apply(key);
        Chain<T> list = data[hash];
        T result = null;
        if (list != null) {
            LinkedList.Node<T> node = list.search(key);
            if (node != null) {
                result = node.getKey();
            }
        }
        return result;
    }

    private static class Chain<T> extends LinkedList<T> {
        @Nullable
        private Node<T> search(int key) {
            Node<T> current = head;
            while (current != null && current.getKey().hashCode() != key) {
                current = current.getNext();
            }
            return current;
        }
    }

    public enum Type {
        DIVISION,
        MULTIPLICATION
    }
}
