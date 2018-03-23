package igorilin13.com.github.main.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static boolean isPostfix(String source, String postfix) {
        int lastIndexOf = source.lastIndexOf(postfix);
        return lastIndexOf >= 0 && lastIndexOf + postfix.length() == source.length();
    }

    public static Set<Character> getAlphabet(String source) {
        Set<Character> result = new HashSet<>();
        for (int i = 0; i < source.length(); i++) {
            result.add(source.charAt(i));
        }
        return result;
    }

    public static int indexOfRegex(String source, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(source);
        return m.find() ? m.start() : -1;
    }
}
