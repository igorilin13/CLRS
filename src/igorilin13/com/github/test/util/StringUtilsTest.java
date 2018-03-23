package igorilin13.com.github.test.util;

import igorilin13.com.github.main.util.StringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

public class StringUtilsTest {
    @Test
    public void testIsPostfix() {
        String source = "abcd";

        assertTrue(StringUtils.isPostfix(source, source.substring(1)));
        assertFalse(StringUtils.isPostfix(source, "dc"));
        assertFalse(StringUtils.isPostfix(source, "a" + source));
        assertTrue(StringUtils.isPostfix(source, ""));

    }

    @Test
    public void testGetAlphabet() {
        assertEquals(new HashSet<>(Arrays.asList('a', 'b', 'c')), StringUtils.getAlphabet("bacacba"));
    }

    @Test
    public void testIndexOfRegex() {
        assertEquals(3, StringUtils.indexOfRegex(" df(sdf)df", "\\("));
    }
}
