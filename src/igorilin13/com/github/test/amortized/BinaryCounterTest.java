package igorilin13.com.github.test.amortized;

import igorilin13.com.github.main.amortized.BinaryCounter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinaryCounterTest {
    @Test
    public void testIncrementDecrement() {
        final BinaryCounter counter = new BinaryCounter();

        final int maxValue = 100;
        for (int i = 1; i <= maxValue; i++) {
            counter.increment();
            assertEquals(i, counter.getCurrent());
        }

        for (int i = maxValue - 1; i >= 1; i--) {
            counter.decrement();
            assertEquals(i, counter.getCurrent());
        }
    }
}