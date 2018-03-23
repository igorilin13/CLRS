package igorilin13.com.github.main.string;

import igorilin13.com.github.main.number.NumberAlgorithms;
import igorilin13.com.github.main.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public class StringMatching {
    public static final int NOT_FOUND = -1;

    private static final int RABIN_KARP_BASE = 256;
    private static final int RABIN_KARP_MOD = 1000;

    public static int naiveMatch(String source, String pattern) {
        int sourceLength = source.length();
        int patternLength = pattern.length();

        for (int shift = 0; shift <= sourceLength - patternLength; shift++) {
            if (source.substring(shift, shift + patternLength).equals(pattern)) {
                return shift;
            }
        }

        return NOT_FOUND;
    }

    public static int rabinKarpMatch(String source, String pattern) {
        if (pattern.isEmpty()) {
            return 0;
        }
        if (source.isEmpty()) {
            return NOT_FOUND;
        }

        int sourceLength = source.length();
        int patternLength = pattern.length();

        long patternHash = 0;
        long sourceHash = 0;
        for (int i = 0; i < patternLength; i++) {
            patternHash = (RABIN_KARP_BASE * patternHash + codePointAt(pattern, i)) % RABIN_KARP_MOD;
            sourceHash = (RABIN_KARP_BASE * sourceHash + codePointAt(source, i)) % RABIN_KARP_MOD;
        }

        int h = NumberAlgorithms.modularExp(RABIN_KARP_BASE, patternLength - 1, RABIN_KARP_MOD);
        for (int shift = 0; shift < sourceLength - patternLength + 1; shift++) {
            if (patternHash == sourceHash) {
                if (source.substring(shift, shift + patternLength).equals(pattern)) {
                    return shift;
                }
            }
            if (shift < sourceLength - patternLength) {
                sourceHash = Math.floorMod((RABIN_KARP_BASE * (sourceHash - codePointAt(source, shift) * h)
                        + codePointAt(source, shift + patternLength)), RABIN_KARP_MOD);
            }
        }

        return NOT_FOUND;
    }

    private static int codePointAt(String source, int index) {
        return source.codePointAt(index) % RABIN_KARP_BASE;
    }

    public static int finiteAutomationMatch(String source, String pattern) {
        if (pattern.isEmpty()) {
            return 0;
        }
        return finiteAutomationMatch(source, computeTransitionFunction(source, pattern), pattern);
    }

    private static int finiteAutomationMatch(String source,
                                             BiFunction<Integer, Character, Integer> transitionFunction,
                                             String pattern) {
        int sourceLength = source.length();
        int patternLength = pattern.length();

        int state = 0;
        for (int i = 0; i < sourceLength; i++) {
            state = transitionFunction.apply(state, source.charAt(i));
            if (state == patternLength) {
                return i - patternLength + 1;
            }
        }

        return NOT_FOUND;
    }

    private static BiFunction<Integer, Character, Integer> computeTransitionFunction(String source,
                                                                                     String pattern) {
        int patternLength = pattern.length();

        Set<Character> alphabet = StringUtils.getAlphabet(source);
        Map<Integer, Map<Character, Integer>> transitionFunction = new HashMap<>();

        for (int state = 0; state <= patternLength; state++) {
            for (char ch : alphabet) {
                String prefix = pattern.substring(0, state) + ch;
                int nextState = Math.min(patternLength, state + 1) + 1;
                do {
                    nextState--;
                } while (!StringUtils.isPostfix(prefix, pattern.substring(0, nextState)));
                if (transitionFunction.containsKey(state)) {
                    transitionFunction.get(state).put(ch, nextState);
                } else {
                    Map<Character, Integer> map = new HashMap<>();
                    map.put(ch, nextState);
                    transitionFunction.put(state, map);
                }
            }
        }

        return (q, a) -> transitionFunction.get(q).get(a);
    }

    public static int kmpMatch(String source, String pattern) {
        if (pattern.isEmpty()) {
            return 0;
        }

        int sourceLength = source.length();
        int patternLength = pattern.length();

        int[] prefixFunction = computePrefixFunction(pattern);
        int charsMatched = 0;
        for (int i = 0; i < sourceLength; i++) {
            while (charsMatched > 0 && pattern.charAt(charsMatched) != source.charAt(i)) {
                charsMatched = prefixFunction[charsMatched - 1];
            }
            if (pattern.charAt(charsMatched) == source.charAt(i)) {
                charsMatched++;
            }
            if (charsMatched == patternLength) {
                return i - patternLength + 1;
            }
        }

        return NOT_FOUND;
    }

    private static int[] computePrefixFunction(String pattern) {
        int patternLength = pattern.length();
        int[] prefixFunction = new int[patternLength];

        int charsMatched = 0;
        for (int i = 1; i < patternLength; i++) {
            while (charsMatched > 0 && pattern.charAt(charsMatched) != pattern.charAt(i)) {
                charsMatched = prefixFunction[charsMatched - 1];
            }
            if (pattern.charAt(charsMatched) == pattern.charAt(i)) {
                charsMatched++;
            }
            prefixFunction[i] = charsMatched;
        }

        return prefixFunction;
    }
}
