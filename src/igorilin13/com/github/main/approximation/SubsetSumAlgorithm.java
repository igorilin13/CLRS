package igorilin13.com.github.main.approximation;

import igorilin13.com.github.main.util.ListUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class SubsetSumAlgorithm {
    private final Set<Integer> values;
    private final int targetSum;
    private final double trimBy;

    private int exactSum;
    private int approximationSum;

    public SubsetSumAlgorithm(Set<Integer> values, int targetSum, double approximationFactor) {
        this.values = values;
        this.targetSum = targetSum;
        trimBy = approximationFactor / (2 * values.size());
    }

    public int computeExactSum() {
        if (exactSum == 0) {
            exactSum = baseComputeSum(null);
        }
        return exactSum;
    }

    public int computeApproximationSum() {
        if (approximationSum == 0) {
            approximationSum = baseComputeSum(this::trim);
        }
        return approximationSum;
    }

    private int baseComputeSum(Function<List<Integer>, List<Integer>> transformMergedList) {
        List<Integer> currentList = new ArrayList<>();
        currentList.add(0);
        for (Integer value : values) {
            currentList = mergeLists(currentList, ListUtils.createIncreased(currentList, value));
            if (transformMergedList != null) {
                currentList = transformMergedList.apply(currentList);
            }
            currentList.removeIf(v -> v > targetSum);
        }
        return currentList.get(currentList.size() - 1);
    }

    private List<Integer> mergeLists(List<Integer> first, List<Integer> second) {
        List<Integer> result = new ArrayList<>(first);
        result.addAll(second);
        result = ListUtils.removeDuplicates(result);
        result.sort(Comparator.naturalOrder());
        return result;
    }

    private List<Integer> trim(List<Integer> list) {
        List<Integer> result = new ArrayList<>();
        int last = list.get(0);
        result.add(last);
        for (int value : list) {
            if (value > last * (1 + trimBy)) {
                result.add(value);
                last = value;
            }
        }
        return result;
    }
}
