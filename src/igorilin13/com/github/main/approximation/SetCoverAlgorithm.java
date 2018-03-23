package igorilin13.com.github.main.approximation;

import java.util.*;

public class SetCoverAlgorithm<E, T> {
    private final Instance<E, T> instance;
    private final List<SubsetCover<E, T>> resultCover;

    public SetCoverAlgorithm(Set<T> values, List<SubsetCover<E, T>> subsets) {
        instance = new Instance<>(values, subsets);

        resultCover = findSetCover();
    }

    private List<SubsetCover<E, T>> findSetCover() {
        Set<T> uncoveredValues = new HashSet<>(instance.values);
        Comparator<SubsetCover<E, T>> coverComparator = createSubsetCoverComparator(uncoveredValues);
        List<SubsetCover<E, T>> setCover = new ArrayList<>();
        while (!uncoveredValues.isEmpty()) {
            SubsetCover<E, T> next = Collections.max(instance.subsets, coverComparator);
            uncoveredValues.removeAll(next.set);
            setCover.add(next);
        }
        return setCover;
    }

    private Comparator<SubsetCover<E, T>> createSubsetCoverComparator(Set<T> uncoveredValues) {
        return (first, second) -> {
            int firstIntersectionSize = 0;
            int secondIntersectionSize = 0;
            for (T value : uncoveredValues) {
                if (first.set.contains(value)) {
                    firstIntersectionSize++;
                }
                if (second.set.contains(value)) {
                    secondIntersectionSize++;
                }
            }
            return Integer.compare(firstIntersectionSize, secondIntersectionSize);
        };
    }

    public List<SubsetCover<E, T>> getResultCover() {
        return resultCover;
    }

    public static <E, T> SubsetCover<E, T> newSubsetCover(E name, Collection<T> values) {
        return new SubsetCover<>(name, new HashSet<>(values));
    }

    private static class Instance<E, T> {
        private final Set<T> values;
        private final List<SubsetCover<E, T>> subsets;

        private Instance(Set<T> values, List<SubsetCover<E, T>> subsets) {
            this.values = values;
            this.subsets = subsets;
        }
    }

    public static class SubsetCover<E, T> {
        private final E name;
        private final Set<T> set;

        private SubsetCover(E name, Set<T> set) {
            this.name = name;
            this.set = set;
        }

        @Override
        public String toString() {
            return name.toString();
        }
    }
}
