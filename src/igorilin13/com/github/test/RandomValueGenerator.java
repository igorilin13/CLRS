package igorilin13.com.github.test;

import java.util.Random;

public class RandomValueGenerator {
    private static final Random RANDOM = new Random();

    public static int randomInt() {
        return randomInt(Short.MIN_VALUE, Short.MAX_VALUE);
    }

    public static int randomInt(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    public static boolean randomBoolean() {
        return randomInt(0, 1) == 1;
    }

    public static char randomChar() {
        return (char) randomInt(0, 255);
    }
}
