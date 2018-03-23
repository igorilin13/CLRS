package igorilin13.com.github.main.amortized;

public class BinaryCounter {
    private static final int DEFAULT_CAPACITY = 32;

    private int[] bits;


    public BinaryCounter() {
        bits = new int[DEFAULT_CAPACITY];
    }

    public void increment() {
        int i = 0;
        while (i < bits.length && bits[i] == 1) {
            bits[i] = 0;
            i++;
        }

        if (i >= bits.length) {
            int[] newArray = new int[2 * bits.length];
            System.arraycopy(bits, 0, newArray, 0, bits.length);
            bits = newArray;
        }
        bits[i] = 1;
    }

    public void decrement() {
        int i = 0;
        while (i < bits.length && bits[i] == 0) {
            bits[i] = 1;
            i++;
        }
        if (i < bits.length) {
            bits[i] = 0;
        }
    }

    public long getCurrent() {
        long result = 0;
        long multiplier = 1;
        for (int bit : bits) {
            result += bit * multiplier;
            multiplier *= 2;
        }
        return result;
    }
}
