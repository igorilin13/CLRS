package igorilin13.com.github.main.arrays;

import igorilin13.com.github.main.datastructures.BinaryHeap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SortingAlgorithms {

    public static void insertionSort(List<Integer> array) {
        for (int j = 1; j < array.size(); j++) {
            int key = array.get(j);
            int i = j - 1;
            while (i >= 0 && array.get(i) > key) {
                array.set(i + 1, array.get(i));
                i--;
            }
            array.set(i + 1, key);
        }
    }

    public static void mergeSort(List<Integer> array) {
        mergeSort(array, 0, array.size() - 1);
    }

    private static void mergeSort(List<Integer> array, int startIndex, int endIndex) {
        if (startIndex < endIndex) {
            int middleIndex = (startIndex + endIndex) / 2;
            mergeSort(array, startIndex, middleIndex);
            mergeSort(array, middleIndex + 1, endIndex);
            merge(array, startIndex, middleIndex, endIndex);
        }
    }

    private static void merge(List<Integer> array, int startIndex, int middleIndex, int endIndex) {
        List<Integer> leftPart = new ArrayList<>(array.subList(startIndex, middleIndex + 1));
        List<Integer> rightPart = new ArrayList<>(array.subList(middleIndex + 1, endIndex + 1));
        leftPart.add(null);
        rightPart.add(null);
        int i = 0;
        int j = 0;
        for (int k = startIndex; k <= endIndex; k++) {
            Integer left = leftPart.get(i);
            Integer right = rightPart.get(j);
            if (right == null || left != null && left < right) {
                array.set(k, left);
                i++;
            } else {
                array.set(k, right);
                j++;
            }
        }
    }

    public static void bubbleSort(List<Integer> array) {
        int size = array.size();
        for (int i = 0; i < size - 2; i++) {
            for (int j = size - 1; j > i; j--) {
                if (array.get(j) < array.get(j - 1)) {
                    Collections.swap(array, j - 1, j);
                }
            }
        }
    }

    public static void heapSort(List<Integer> array) {
        BinaryHeap<Integer> heap = BinaryHeap.createMaxHeap(array);
        for (int i = array.size() - 1; i > 0; i--) {
            Collections.swap(array, 0, i);
            heap.decreaseSize();
            heap.heapify(0);
        }
    }

    public static void quickSort(List<Integer> array) {
        quickSort(array, 0, array.size() - 1);
    }

    private static void quickSort(List<Integer> array, int p, int r) {
        if (p < r) {
            int q = partition(array, p, r);
            quickSort(array, p, q - 1);
            quickSort(array, q + 1, r);
        }
    }

    private static int partition(List<Integer> array, int p, int r) {
        int x = array.get(r);
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (array.get(j) <= x) {
                i++;
                Collections.swap(array, i, j);
            }
        }
        Collections.swap(array, i + 1, r);
        return i + 1;
    }

    public static void randomizedQuickSort(List<Integer> array) {
        randomizedQuickSort(array, 0, array.size() - 1);
    }

    private static void randomizedQuickSort(List<Integer> array, int p, int r) {
        if (p < r) {
            int q = randomizedPartition(array, p, r);
            randomizedQuickSort(array, p, q - 1);
            randomizedQuickSort(array, q + 1, r);
        }
    }

    static int randomizedPartition(List<Integer> array, int p, int r) {
        if (r <= p) {
            return 0;
        }
        int i = ThreadLocalRandom.current().nextInt(p, r + 1);
        Collections.swap(array, i, r);
        return partition(array, p, r);
    }

    public static void countingSort(List<Integer> array) {
        int max = Collections.max(array) + 1;
        List<Integer> aux = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            aux.add(0);
        }
        for (int index : array) {
            aux.set(index, aux.get(index) + 1);
        }
        for (int i = 1; i < max; i++) {
            aux.set(i, aux.get(i) + aux.get(i - 1));
        }
        List<Integer> sorted = new ArrayList<>(array);
        for (int j = array.size() - 1; j >= 0; j--) {
            sorted.set(aux.get(array.get(j)) - 1, array.get(j));
            aux.set(array.get(j), aux.get(array.get(j)) - 1);
        }
        for (int i = 0; i < array.size(); i++) {
            array.set(i, sorted.get(i));
        }
    }

    public static void radixSort(List<Integer> array) {
        int maxDigits = String.valueOf(Collections.max(array)).length();
        List<String> stringCopy = new ArrayList<>(array.size());
        for (int item : array) {
            stringCopy.add(String.valueOf(item));
        }
        for (int i = 0; i < maxDigits; i++) {
            sortByDigit(stringCopy, i);
        }
        for (int i = 0; i < array.size(); i++) {
            array.set(i, Integer.parseInt(stringCopy.get(i)));
        }
    }

    private static void sortByDigit(List<String> array, int indexFromLowestDigit) {
        array.sort((first, second) -> {
            int firstDigit = first.length() - indexFromLowestDigit - 1 >= 0 ?
                    first.codePointAt(first.length() - indexFromLowestDigit - 1) : -1;
            int secondDigit = second.length() - indexFromLowestDigit - 1 >= 0 ?
                    second.codePointAt(second.length() - indexFromLowestDigit - 1) : -1;
            if (firstDigit == secondDigit) {
                return 0;
            }
            return firstDigit > secondDigit ? 1 : -1;
        });
    }

    public static void bucketSort(List<Integer> array) {
        final int totalBuckets = 10;
        int max = Collections.max(array);
        List<List<Integer>> buckets = new ArrayList<>(totalBuckets);
        for (int i = 0; i < totalBuckets; i++) {
            buckets.add(new LinkedList<>());
        }
        for (int item : array) {
            int bucket = (totalBuckets - 1) * item / max;
            buckets.get(bucket).add(item);
        }
        for (int i = 0; i < totalBuckets; i++) {
            quickSort(buckets.get(i));
        }
        int i = 0;
        for (List<Integer> list : buckets) {
            for (Integer item : list) {
                array.set(i, item);
                i++;
            }
        }
    }

    public static void selectionSort(List<Integer> array) {
        int size = array.size();
        for (int i = 0; i < size - 1; i++) {
            int min = i;
            for (int j = i + 1; j < size; j++) {
                if (array.get(j) < array.get(min)) {
                    min = j;
                }
            }
            Collections.swap(array, i, min);
        }
    }
}