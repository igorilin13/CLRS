package igorilin13.com.github.test;

import igorilin13.com.github.main.polynomial.Complex;
import igorilin13.com.github.main.util.ArrayUtils;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class Assert {
    public static void assertEquals(double[][] expected, double[][] actual, double precision) {
        org.junit.Assert.assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals(expected[i], actual[i], precision);
        }
    }

    public static void assertEquals(List<Complex> expected, List<Complex> actual, double precision) {
        org.junit.Assert.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i), precision);
        }
    }

    public static void assertEquals(Complex expected, Complex actual, double precision) {
        org.junit.Assert.assertEquals(expected.getRe(), actual.getRe(), precision);
        org.junit.Assert.assertEquals(expected.getIm(), actual.getIm(), precision);
    }

    public static void assertArrayHasSameValues(int[] expected, int[] result) {
        assertArrayEquals(ArrayUtils.copySort(expected), ArrayUtils.copySort(result));
    }
}
