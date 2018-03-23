package igorilin13.com.github.test.string;

import igorilin13.com.github.main.string.StringMatching;
import igorilin13.com.github.test.RandomValueGenerator;
import org.junit.Test;

import java.util.function.BiFunction;

import static org.junit.Assert.assertEquals;

public class StringMatchingTest {
    private static final String PATTERN = "ababacaabasdferewpomk";
    private static final int MATCH_ITERATIONS = 100;

    @Test
    public void testNaiveMatch() {
        testMatching(StringMatching::naiveMatch);
    }

    @Test
    public void testRabinKarpMatch() {
        testMatching(StringMatching::rabinKarpMatch);
    }

    @Test
    public void testFiniteAutomationMatch() {
        testMatching(StringMatching::finiteAutomationMatch);
    }

    @Test
    public void testKmpMatch() {
        testMatching(StringMatching::kmpMatch);
    }

    private void testMatching(BiFunction<String, String, Integer> match) {
        assertEquals(StringMatching.NOT_FOUND, (int) match.apply("", PATTERN));
        assertEquals(0, (int) match.apply("aaa", ""));
        assertEquals(0, (int) match.apply("", ""));

        assertEquals(0, (int) match.apply(PATTERN, PATTERN));

        StringBuilder prefix = new StringBuilder();
        StringBuilder postfix = new StringBuilder();
        for (int i = 0; i < MATCH_ITERATIONS; i++) {
            prefix.append(RandomValueGenerator.randomChar());
            postfix.append(RandomValueGenerator.randomChar());

            assertEquals(0, (int) match.apply(PATTERN + postfix, PATTERN));
            assertEquals(prefix.length(), (int) match.apply(prefix + PATTERN, PATTERN));
            assertEquals(prefix.length(), (int) match.apply(prefix + PATTERN + postfix, PATTERN));
        }
    }
}