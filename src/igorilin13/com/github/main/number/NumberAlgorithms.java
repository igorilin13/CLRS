package igorilin13.com.github.main.number;

import igorilin13.com.github.main.util.TupleN;
import igorilin13.com.github.main.util.UniqueRandomInt;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class NumberAlgorithms {
    public static int euclid(int a, int b) {
        if (a < 0 || b < 0) {
            throw new IllegalArgumentException("Precondition failed: a, b >= 0");
        }
        if (b == 0) {
            return a;
        }
        return euclid(b, a % b);
    }

    /**
     * @return (d, x, y): d = ax + by
     */
    public static TupleN<Integer> extEuclid(int a, int b) {
        if (a < 0 || b < 0) {
            throw new IllegalArgumentException("Precondition failed: a, b >= 0");
        }
        if (b == 0) {
            return new TupleN<>(a, 1, 0);
        }
        TupleN<Integer> tmp = extEuclid(b, a % b);
        return new TupleN<>(tmp.get(0), tmp.get(2), tmp.get(1) - (a / b) * tmp.get(2));
    }

    /**
     * @return {x | a * x ≡ b (mod n)}
     */
    @Nullable
    public static int[] modularSolve(int a, int b, int n) {
        if (a <= 0 || n <= 0) {
            throw new IllegalArgumentException("Precondition failed: a, n > 0");
        }
        TupleN<Integer> extGcd = extEuclid(a, n);
        int gcd = extGcd.get(0);
        if (b % gcd == 0) {
            int[] res = new int[gcd];
            int x0 = Math.floorMod(extGcd.get(1) * (b / gcd), n);
            for (int i = 0; i < gcd; i++) {
                res[i] = (x0 + i * (n / gcd)) % n;
            }
            return res;
        } else {
            return null;
        }
    }

    /**
     * @return a ^ b mod n
     */
    public static int modularExp(int a, int b, int n) {
        if (a < 0 || b < 0 || n <= 0) {
            throw new IllegalArgumentException("Precondition failed: a, b >= 0 && n > 0");
        }
        int res = 1;
        String binary = Integer.toBinaryString(b);
        for (int i = 0; i < binary.length(); i++) {
            res = res * res % n;
            if (binary.charAt(i) == '1') {
                res = res * a % n;
            }
        }
        return res;
    }

    public static boolean isPseudoPrime(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Precondition failed: n >= 1");
        }
        return n == 2 || n % 2 != 0 && modularExp(2, n - 1, n) % n == 1;
    }

    public static boolean isMillerRabinPrime(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Precondition failed: n >= 1");
        }
        return n == 2 || n != 1 && n % 2 != 0 && checkMillerRabinPrime(n);
    }

    private static boolean checkMillerRabinPrime(int n) {
        int base = 10;
        UniqueRandomInt randomInt = new UniqueRandomInt(1, n);
        for (int j = 0; j < Math.min(base, n - 1); j++) {
            if (isWitnessComposite(randomInt.next(), n)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isWitnessComposite(int a, int n) {
        //u * 2^t == n - 1
        String binary = Integer.toBinaryString(n - 1);
        int t = 0;
        int i;
        for (i = binary.length() - 1; i >= 0 && binary.charAt(i) == '0'; i--) {
            t++;
        }
        int u = Integer.parseInt(binary.substring(0, i + 1), 2);

        int prev = modularExp(a, u, n);
        int current = prev;
        for (i = 1; i <= t; i++) {
            current = Math.floorMod(prev * prev, n);
            if (current == 1 && prev != 1 && prev != n - 1) {
                return true;
            }
            prev = current;
        }
        return current != 1;
    }

    /**
     * This algorithm is only a heuristic, meaning neither its running time nor its success is guaranteed.
     */
    public static int pollardRhoFactor(int n, int timeoutSeconds) throws TimeoutException {
        if (n <= 2) {
            throw new IllegalArgumentException("Precondition failed: n > 2");
        }

        long startTime = System.nanoTime();
        long timeoutNano = TimeUnit.NANOSECONDS.convert(timeoutSeconds, TimeUnit.SECONDS);

        if (n % 2 == 0) {
            return 2;
        }
        if (isMillerRabinPrime(n)) {
            return n;
        }

        int iteration = 1;
        int prev = ThreadLocalRandom.current().nextInt(0, n);
        int saved = prev;
        int nextSaveIteration = 2;
        while (System.nanoTime() - startTime < timeoutNano) {
            iteration++;
            int current = Math.floorMod(prev * prev - 1, n);
            int gcd = euclid(Math.abs(saved - current), n);
            if (gcd != 1 && gcd != n) {
                return gcd;
            }
            if (iteration == nextSaveIteration) {
                saved = current;
                nextSaveIteration *= 2;
            }
            prev = current;
        }

        throw new TimeoutException("Couldn't find the factor for " + n);
    }
}
