package igorilin13.com.github.main.util;

public final class MathUtils {
    private MathUtils() {
    }

    public static double log2(int x) {
        return 31 - Integer.numberOfLeadingZeros(x);
    }

    public static int pow2(int x) {
        return 1 << x;
    }

    public static boolean isPowerOf2(int x) {
        return x != 0 && (x & (x - 1)) == 0;
    }

    public static boolean isEquals(double d1, double d2, double precision) {
        return Double.compare(d1, d2) == 0 || Math.abs(d1 - d2) <= precision;
    }
}
