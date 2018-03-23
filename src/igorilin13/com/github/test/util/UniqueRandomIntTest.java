package igorilin13.com.github.test.util;

import igorilin13.com.github.main.util.UniqueRandomInt;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class UniqueRandomIntTest {
    private static final int LEAST = -10;
    private static final int BOUND = 1000;

    private final UniqueRandomInt randomInt = new UniqueRandomInt(LEAST, BOUND);
    private final List<Integer> used = new ArrayList<>();

    @Test
    public void testRandomInt() {
        for (int i = 0; i < BOUND - LEAST; i++) {
            int next = randomInt.next();
            if (used.contains(next)) {
                fail("Value " + next + " was already used");
            }
            used.add(next);
        }
    }
}
