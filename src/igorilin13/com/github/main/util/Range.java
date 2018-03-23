package igorilin13.com.github.main.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Range {
    public static List<Integer> ofInt(int min, int bound) {
        return IntStream.range(min, bound).boxed().collect(Collectors.toList());
    }
}
