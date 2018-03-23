package igorilin13.com.github.test.greedy;

import igorilin13.com.github.main.greedy.ActivitySelector;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ActivitySelectorTest {
    private static final int[] activityStart = {0, 1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12};
    private static final int[] activityFinish = {0, 4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16};
    private static final Set<String> expectedResult;

    static {
        expectedResult = new HashSet<>();
        expectedResult.add("[1, 4, 8, 11]");
        expectedResult.add("[2, 4, 9, 11]");
    }

    @Test
    public void testRecursiveActivitySelector() {
        Set<Integer> result = ActivitySelector.recursiveActivitySelector(activityStart, activityFinish);
        assertNotNull(result);
        assertTrue(expectedResult.contains(result.toString()));
    }

    @Test
    public void testGreedyActivitySelector() {
        Set<Integer> result = ActivitySelector.greedyActivitySelector(activityStart, activityFinish);
        assertNotNull(result);
        assertTrue(expectedResult.contains(result.toString()));
    }
}
