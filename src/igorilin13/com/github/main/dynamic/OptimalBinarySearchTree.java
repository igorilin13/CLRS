package igorilin13.com.github.main.dynamic;

import igorilin13.com.github.main.util.TupleN;

public class OptimalBinarySearchTree {
    public static TupleN<int[][]> optimalBst(int[] keyProbability, int[] dummyKeyProbability) {
        int keysNumbers = keyProbability.length;
        int[][] searchCost = new int[keysNumbers + 1][keysNumbers + 1];
        int[][] probabilitiesSum = new int[keysNumbers + 1][keysNumbers + 1];
        int[][] root = new int[keysNumbers][keysNumbers];

        for (int i = 1; i <= keysNumbers + 1; i++) {
            searchCost[i - 1][i - 1] = probabilitiesSum[i - 1][i - 1] = dummyKeyProbability[i - 1];
        }

        for (int k = 1; k <= keysNumbers; k++) {
            for (int i = 1; i <= keysNumbers - k + 1; i++) {
                int j = i + k - 1;
                searchCost[i - 1][j] = Integer.MAX_VALUE;
                probabilitiesSum[i - 1][j] = probabilitiesSum[i - 1][j - 1] + keyProbability[j - 1] + dummyKeyProbability[j];
                for (int r = i; r <= j; r++) {
                    int t = searchCost[i - 1][r - 1] + searchCost[r][j] + probabilitiesSum[i - 1][j];
                    if (t < searchCost[i - 1][j]) {
                        searchCost[i - 1][j] = t;
                        root[i - 1][j - 1] = r;
                    }
                }
            }
        }

        return new TupleN<>(searchCost, root);
    }
}
