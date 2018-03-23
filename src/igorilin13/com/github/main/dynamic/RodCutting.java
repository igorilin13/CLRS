package igorilin13.com.github.main.dynamic;

import igorilin13.com.github.main.util.TupleN;

public class RodCutting {
    private static final int INT_UNDEFINED = -1;

    public static int cutRod(int[] price) {
        return cutRod(price, price.length);
    }

    private static int cutRod(int[] price, int length) {
        if (length == 0) {
            return 0;
        }

        int result = INT_UNDEFINED;
        for (int i = 0; i < length; i++) {
            result = Math.max(result, price[i] + cutRod(price, length - i - 1));
        }

        return result;
    }

    public static int memoizedCutRod(int[] price) {
        int length = price.length;
        int[] knownResults = new int[length];
        for (int i = 0; i < length; i++) {
            knownResults[i] = INT_UNDEFINED;
        }
        return memoizedCutRodAux(price, length, knownResults);
    }

    private static int memoizedCutRodAux(int[] price, int length, int[] knownResults) {
        if (length > 0 && knownResults[length - 1] >= 0) {
            return knownResults[length - 1];
        }

        int result;
        if (length == 0) {
            result = 0;
        } else {
            result = INT_UNDEFINED;
            for (int i = 0; i < length; i++) {
                result = Math.max(result, price[i] + memoizedCutRodAux(price, length - i - 1, knownResults));
            }
        }

        if (length > 0) {
            knownResults[length - 1] = result;
        }

        return result;
    }

    public static int bottomUpCutRod(int[] price) {
        int length = price.length;
        int[] knownResults = new int[length + 1];
        knownResults[0] = 0;
        for (int j = 0; j < length; j++) {
            int result = INT_UNDEFINED;
            for (int i = 0; i <= j; i++) {
                result = Math.max(result, price[i] + knownResults[j - i]);
            }
            knownResults[j + 1] = result;
        }
        return knownResults[length];
    }

    public static String getCutRodSolution(int[] price) {
        int length = price.length;
        TupleN<int[]> solution = extendedBottomUpCutRod(price);
        //int[] maxPrices = solution.get(0);
        int[] solutionPath = solution.get(1);

        StringBuilder res = new StringBuilder();
        while (length > 0) {
            res.append(solutionPath[length]);
            length -= solutionPath[length];
        }
        return res.toString();
    }

    public static TupleN<int[]> extendedBottomUpCutRod(int[] price) {
        int length = price.length;
        int[] maxPrices = new int[length + 1];
        int[] solutionsPath = new int[length + 1];

        maxPrices[0] = 0;
        int q = INT_UNDEFINED;
        for (int j = 0; j < length; j++) {
            for (int i = 0; i <= j; i++) {
                if (q < price[i] + maxPrices[j - i]) {
                    q = price[i] + maxPrices[j - i];
                    solutionsPath[j + 1] = i + 1;
                }
            }
            maxPrices[j + 1] = q;
        }
        return new TupleN<>(maxPrices, solutionsPath);
    }
}
