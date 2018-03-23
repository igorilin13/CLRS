package igorilin13.com.github.test.greedy;

import igorilin13.com.github.main.greedy.HuffmanCoding;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class HuffmanCodingTest {
    private static final Set<HuffmanCoding.Element<Character>> TEST_CASE = new HashSet<>(Arrays.asList(
            new HuffmanCoding.Element<>('a', 45),
            new HuffmanCoding.Element<>('b', 13),
            new HuffmanCoding.Element<>('c', 12),
            new HuffmanCoding.Element<>('d', 16),
            new HuffmanCoding.Element<>('e', 9),
            new HuffmanCoding.Element<>('f', 5)
    ));

    private static final Map<Character, String> EXPECTED_RESULT;

    static {
        EXPECTED_RESULT = new HashMap<>();
        EXPECTED_RESULT.put('a', "0");
        EXPECTED_RESULT.put('b', "101");
        EXPECTED_RESULT.put('c', "100");
        EXPECTED_RESULT.put('d', "111");
        EXPECTED_RESULT.put('e', "1101");
        EXPECTED_RESULT.put('f', "1100");
    }

    @Test
    public void testBuildCodes() {
        Set<HuffmanCoding.ElementCode<Character>> codes = HuffmanCoding.buildCodes(TEST_CASE);
        for (HuffmanCoding.ElementCode<Character> code : codes) {
            assertEquals(EXPECTED_RESULT.get(code.getElement().getValue()), code.getCode());
        }
    }
}
