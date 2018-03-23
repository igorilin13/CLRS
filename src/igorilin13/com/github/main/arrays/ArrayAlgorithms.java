package igorilin13.com.github.main.arrays;

import igorilin13.com.github.main.util.TupleN;

import java.util.ArrayList;
import java.util.List;

public class ArrayAlgorithms {

    public static TupleN<Integer> maxSubarray(int[] a) {
        return maxSubarray(a, new TupleN<>(0, a.length - 1));
    }

    private static TupleN<Integer> maxSubarray(int[] array, TupleN<Integer> index) {
        int low = index.get(0);
        int high = index.get(1);

        if (low == high) {
            return new TupleN<>(low, high, array[low]);
        } else {
            int mid = (low + high) >>> 1;
            TupleN<Integer> leftPartMax = maxSubarray(array, new TupleN<>(low, mid));
            int leftLow = leftPartMax.get(0);
            int leftHigh = leftPartMax.get(1);
            int leftSum = leftPartMax.get(2);

            TupleN<Integer> rightPartMax = maxSubarray(array, new TupleN<>(mid + 1, high));
            int rightLow = rightPartMax.get(0);
            int rightHigh = rightPartMax.get(1);
            int rightSum = rightPartMax.get(2);

            TupleN<Integer> crossingMax = maxCrossingSubarray(array, new TupleN<>(low, mid, high));
            int crossLow = crossingMax.get(0);
            int crossHigh = crossingMax.get(1);
            int crossSum = crossingMax.get(2);

            if (leftSum >= rightSum && leftSum >= crossSum) {
                return new TupleN<>(leftLow, leftHigh, leftSum);
            } else if (rightSum >= leftSum && rightSum >= crossSum) {
                return new TupleN<>(rightLow, rightHigh, rightSum);
            } else {
                return new TupleN<>(crossLow, crossHigh, crossSum);
            }
        }
    }

    private static TupleN<Integer> maxCrossingSubarray(int[] array, TupleN<Integer> index) {
        int low = index.get(0);
        int mid = index.get(1);
        int high = index.get(2);

        int maxLeft = mid;
        int leftSum = array[mid];
        int sum = leftSum;
        for (int i = mid + 1; i >= low; i--) {
            sum += array[i];
            if (sum > leftSum) {
                leftSum = sum;
                maxLeft = i;
            }
        }
        int rightSum = array[mid + 1];
        int maxRight = mid + 1;
        sum = rightSum;
        for (int j = mid + 2; j <= high; j++) {
            sum += array[j];
            if (sum > rightSum) {
                rightSum = sum;
                maxRight = j;
            }
        }
        return new TupleN<>(maxLeft, maxRight, leftSum + rightSum);
    }

    public static int[][] squareMatrixMultiply(int[][] first, int[][] second) {
        int n = first.length;
        int[][] res = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = 0;
                for (int k = 0; k < n; k++) {
                    res[i][j] += first[i][k] * second[k][j];
                }
            }
        }
        return res;
    }

    public static int binarySearch(int[] array, int startIndex, int size, int key) {
        int low = startIndex;
        int high = Math.max(startIndex, startIndex + size - 1);
        while (low < high) {
            int mid = (low + high) >>> 1;
            if (key <= array[mid]) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return high;
    }

    /**
     * @return Returns ith smallest element in the array.
     */
    public static int randomizedSelect(List<Integer> array, int i) {
        return randomizedSelect(new ArrayList<>(array), 0, array.size() - 1, i);
    }

    private static int randomizedSelect(List<Integer> array, int startIndex, int endIndex, int i) {
        if (startIndex == endIndex) {
            return array.get(startIndex);
        }
        int pivotIndex = SortingAlgorithms.randomizedPartition(array, startIndex, endIndex);
        int subarraySize = pivotIndex - startIndex + 1;
        if (i == subarraySize - 1) {
            return array.get(pivotIndex);
        } else if (i < subarraySize) {
            return randomizedSelect(array, startIndex, pivotIndex - 1, i);
        } else {
            return randomizedSelect(array, pivotIndex + 1, endIndex, i - subarraySize);
        }
    }
}