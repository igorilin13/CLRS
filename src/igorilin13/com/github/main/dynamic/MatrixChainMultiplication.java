package igorilin13.com.github.main.dynamic;

import igorilin13.com.github.main.util.TupleN;

public class MatrixChainMultiplication {
    public static final String MATRIX_NAME = "A";

    public static TupleN<int[][]> matrixChainOrder(int[] dimensions) {
        int n = dimensions.length - 1;
        int[][] minCost = new int[n][n];
        int[][] optimalIndex = new int[n - 1][n - 1];

        for (int chainLength = 2; chainLength <= n; chainLength++) {
            for (int i = 1; i <= n - chainLength + 1; i++) {
                int j = i + chainLength - 1;
                minCost[i - 1][j - 1] = Integer.MAX_VALUE;
                for (int k = i; k <= j - 1; k++) {
                    int q = minCost[i - 1][k - 1] + minCost[k][j - 1] + dimensions[i - 1] * dimensions[k] * dimensions[j];
                    if (q < minCost[i - 1][j - 1]) {
                        minCost[i - 1][j - 1] = q;
                        optimalIndex[i - 1][j - 2] = k;
                    }
                }
            }
        }

        return new TupleN<>(minCost, optimalIndex);
    }

    public static String getOptimalParens(int[] dimensions) {
        return getOptimalParens(matrixChainOrder(dimensions).get(1), 1, dimensions.length - 1);
    }

    private static String getOptimalParens(int[][] optimalIndex, int i, int j) {
        return i == j ? MATRIX_NAME : "(" +
                getOptimalParens(optimalIndex, i, optimalIndex[i - 1][j - 2]) +
                getOptimalParens(optimalIndex, optimalIndex[i - 1][j - 2] + 1, j) +
                ")";
    }
}
