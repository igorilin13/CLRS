package igorilin13.com.github.test.dynamic;

import igorilin13.com.github.main.dynamic.LongestCommonSubsequence;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LongestCommonSubsequenceTest {
    @Test
    public void testLcs() {
        assertEquals("BCBA", LongestCommonSubsequence.getLcs("ABCBDAB", "BDCABA"));
        assertEquals("GTCGTCGGAAGCCGGCCGAA",
                LongestCommonSubsequence.getLcs("ACCGGTCGAGTGCGCGGAAGCCGGCCGAA", "GTCGTTCGGAATGCCGTTGCTCTGTAAA"));
    }
}
