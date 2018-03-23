package igorilin13.com.github.test.approximation;

import igorilin13.com.github.main.approximation.SetCoverAlgorithm;
import igorilin13.com.github.main.util.Range;
import org.junit.Test;

import java.util.*;

import static igorilin13.com.github.main.approximation.SetCoverAlgorithm.newSubsetCover;
import static org.junit.Assert.assertTrue;

public class SetCoverAlgorithmTest {
    private static final Set<Integer> VALUES = new HashSet<>(Range.ofInt(0, 12));
    private static final List<SetCoverAlgorithm.SubsetCover<String, Integer>> SUBSETS = new ArrayList<>();

    static {
        SUBSETS.add(newSubsetCover("S1", Arrays.asList(0, 1, 4, 5, 8, 9)));
        SUBSETS.add(newSubsetCover("S2", Arrays.asList(5, 6, 9, 10)));
        SUBSETS.add(newSubsetCover("S3", Arrays.asList(0, 1, 2, 3)));
        SUBSETS.add(newSubsetCover("S4", Arrays.asList(4, 5, 6, 7, 2)));
        SUBSETS.add(newSubsetCover("S5", Arrays.asList(8, 9, 10, 11)));
        SUBSETS.add(newSubsetCover("S6", Arrays.asList(3, 7)));
    }

    private static final List<String> EXPECTED_SOLUTIONS = Arrays.asList(
            "[S1, S4, S5, S3]",
            "[S1, S4, S5, S6]"
    );

    @Test
    public void testSetCover() {
        SetCoverAlgorithm<String, Integer> algorithm = new SetCoverAlgorithm<>(VALUES, SUBSETS);
        assertTrue(EXPECTED_SOLUTIONS.contains(algorithm.getResultCover().toString()));
    }
}
