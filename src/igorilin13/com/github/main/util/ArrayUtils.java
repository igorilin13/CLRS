package igorilin13.com.github.main.util;

import java.util.Arrays;

public final class ArrayUtils {
    private ArrayUtils() {
    }

    public static <T> T[] extendCopy(T[] source, int newCapacity) {
        T[] copy = (T[]) new Object[newCapacity];
        System.arraycopy(source, 0, copy, 0, source.length);
        return copy;
    }

    public static double[][] copy(double[][] source) {
        int size = source.length;
        double[][] result = new double[size][];
        for (int i = 0; i < size; i++) {
            int rowSize = source[i].length;
            result[i] = new double[rowSize];
            System.arraycopy(source[i], 0, result[i], 0, rowSize);
        }
        return result;
    }

    public static int[][] copy(int[][] source) {
        int size = source.length;
        int[][] result = new int[size][];
        for (int i = 0; i < size; i++) {
            int rowSize = source[i].length;
            result[i] = new int[rowSize];
            System.arraycopy(source[i], 0, result[i], 0, rowSize);
        }
        return result;
    }

    public static int[] copySort(int[] source) {
        int[] res = copy(source);
        Arrays.sort(res);
        return res;
    }

    public static int[] copy(int[] source) {
        int[] res = new int[source.length];
        System.arraycopy(source, 0, res, 0, source.length);
        return res;
    }

    public static double[] copy(double[] source) {
        double[] res = new double[source.length];
        System.arraycopy(source, 0, res, 0, source.length);
        return res;
    }

    public static int minIndex(double[] array) {
        int res = array.length > 0 ? 0 : -1;
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[res]) {
                res = i;
            }
        }
        return res;
    }

    public static boolean contains(int[] array, int value) {
        for (int item : array) {
            if (item == value) {
                return true;
            }
        }
        return false;
    }
}
