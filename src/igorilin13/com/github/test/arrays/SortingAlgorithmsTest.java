package igorilin13.com.github.test.arrays;

import igorilin13.com.github.main.arrays.SortingAlgorithms;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.fail;

public class SortingAlgorithmsTest {
    private static final int ARRAY_SIZE = 10000;

    private static List<Integer> array;

    @BeforeClass
    public static void setUpClass() {
        array = new ArrayList<>(ARRAY_SIZE);
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array.add(i);
        }
    }

    @Test
    public void testInsertionSort() {
        testArraySorting(SortingAlgorithms::insertionSort);
    }

    @Test
    public void testMergeSort() {
        testArraySorting(SortingAlgorithms::mergeSort);
    }

    @Test
    public void testBubbleSort() {
        testArraySorting(SortingAlgorithms::bubbleSort);
    }

    @Test
    public void testHeapSort() {
        testArraySorting(SortingAlgorithms::heapSort);
    }

    @Test
    public void testQuickSort() {
        testArraySorting(SortingAlgorithms::quickSort);
    }

    @Test
    public void testRandomizedQuickSort() {
        testArraySorting(SortingAlgorithms::randomizedQuickSort);
    }

    @Test
    public void testCountingSort() {
        testArraySorting(SortingAlgorithms::countingSort);
    }

    @Test
    public void testRadixSort() {
        testArraySorting(SortingAlgorithms::radixSort);
    }

    @Test
    public void testBucketSort() {
        testArraySorting(SortingAlgorithms::bucketSort);
    }

    @Test
    public void testSelectionSort() {
        testArraySorting(SortingAlgorithms::selectionSort);
    }

    private void testArraySorting(Consumer<List<Integer>> sort) {
        Collections.shuffle(array);
        sort.accept(array);
        testArrayOrder();
    }

    private void testArrayOrder() {
        for (int i = 0; i < ARRAY_SIZE; i++) {
            if (array.get(i) != i) {
                System.out.println(Arrays.toString(array.toArray()));
                fail(array.get(i) + " != " + i);
            }
        }
    }
}
