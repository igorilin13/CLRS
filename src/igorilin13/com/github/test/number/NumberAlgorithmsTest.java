package igorilin13.com.github.test.number;

import igorilin13.com.github.main.number.NumberAlgorithms;
import igorilin13.com.github.main.util.TupleN;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

import static igorilin13.com.github.main.number.NumberAlgorithms.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class NumberAlgorithmsTest {
    @Test
    public void testEuclid() {
        final int a = 30;
        final int b = 21;
        final int gcd = 3;
        assertEquals(gcd, euclid(a, b));
    }

    @Test
    public void testExtendedEuclid() {
        final int a = 99;
        final int b = 78;
        final TupleN<Integer> expected = new TupleN<>(3, -11, 14);
        assertEquals(expected, extEuclid(a, b));
    }

    @Test
    public void testModularSolve() {
        final int a = 14;
        final int b = 30;
        final int n = 100;
        final int[] expected = {95, 45};
        assertArrayEquals(expected, modularSolve(a, b, n));
    }

    @Test
    public void testModularExp() {
        final int a = 7;
        final int b = 560;
        final int n = 561;
        final int expected = 1;
        assertEquals(expected, NumberAlgorithms.modularExp(a, b, n));
    }

    @Test
    public void testPseudoPrime() {
        testPrimality(NumberAlgorithms::isPseudoPrime);
    }

    @Test
    public void testMillerRabinPrime() {
        testPrimality(NumberAlgorithms::isMillerRabinPrime);
    }

    private void testPrimality(Predicate<Integer> isPrime) {
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
        int max = Collections.max(primes);
        for (int i = 1; i <= max; i++) {
            assertEquals("Failed for " + i, primes.contains(i), isPrime.test(i));
        }
    }

    @Test(timeout = 60 * 1000)
    public void testPollardRhoFactor() {
        final int least = 100000;
        final int size = 100;
        final int timeoutSeconds = 1;
        int fails = 0;
        for (int i = least; i < least + size; i++) {
            try {
                int res = NumberAlgorithms.pollardRhoFactor(i, timeoutSeconds);
                assertEquals(0, i % res);
            } catch (TimeoutException e) {
                System.out.println(e.getMessage());
                fails++;
            }
        }
        System.out.println(String.format("Fail rate: %d/%d", fails, size));
    }
}
