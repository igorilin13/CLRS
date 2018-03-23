package igorilin13.com.github.main.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public final class ListUtils {
    private ListUtils() {
    }

    public static <T> String toString(List<T> list) {
        return list.stream().map(Object::toString).collect(Collectors.joining(""));
    }

    public static <T> void extendInsert(List<T> list, int insertIndex, T insertValue) {
        if (list.size() <= insertIndex) {
            for (int i = list.size(); i < insertIndex; i++) {
                list.add(i, null);
            }
            list.add(insertIndex, insertValue);
        } else {
            list.set(insertIndex, insertValue);
        }
    }

    public static List<Integer> createIncreased(List<Integer> list, int increaseBy) {
        List<Integer> result = new ArrayList<>(list.size());
        for (int value : list) {
            result.add(value + increaseBy);
        }
        return result;
    }

    public static <T> List<T> removeDuplicates(List<T> list) {
        return new ArrayList<>(new HashSet<>(list));
    }

    public static <T> List<T> createWith(T value, int size) {
        List<T> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(value);
        }
        return result;
    }
}
