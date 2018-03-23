package igorilin13.com.github.test.arrays;

import igorilin13.com.github.main.arrays.ArrayAlgorithms;
import igorilin13.com.github.main.util.Range;
import igorilin13.com.github.main.util.TupleN;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ArrayAlgorithmsTest {

    @Test
    public void testMaxSubarray() {
        int[] array = {13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};
        TupleN<Integer> res = ArrayAlgorithms.maxSubarray(array);
        assertEquals((int) res.get(0), 7);
        assertEquals((int) res.get(1), 10);
    }

    @Test
    public void testSquareMatrixMultiply() {
        int a[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int b[][] = {{10, 11, 12}, {13, 14, 15}, {16, 17, 18}};
        int expectedRes[][] = {{84, 90, 96}, {201, 216, 231}, {318, 342, 366}};
        int res[][] = ArrayAlgorithms.squareMatrixMultiply(a, b);
        assertArrayEquals(expectedRes, res);
    }

    @Test
    public void testBinarySearch() {
        final int size = 1000;
        final int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i * 2;
        }

        for (int i = 0; i < size; i++) {
            assertEquals(i, ArrayAlgorithms.binarySearch(array, 0, size, i * 2 - 1));
            assertEquals(i, ArrayAlgorithms.binarySearch(array, 0, size, i * 2));
        }
        assertEquals(3, ArrayAlgorithms.binarySearch(array, 3, 0, 5));
    }

    @Test
    public void testRandomizedSelect() {
        final int minValue = 0;
        final int maxValue = 1000;

        List<Integer> values = Range.ofInt(minValue, maxValue + 1);
        Collections.shuffle(values);

        for (int i = minValue; i <= maxValue; i++) {
            assertEquals(i, ArrayAlgorithms.randomizedSelect(values, i));
        }
    }
}