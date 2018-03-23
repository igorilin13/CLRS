package igorilin13.com.github.main.dynamic;

import igorilin13.com.github.main.util.TupleN;

public class LongestCommonSubsequence {
    public static String getLcs(String firstSequence, String secondSequence) {
        int[][] directions = lcsLength(firstSequence, secondSequence).get(0);
        return getLcs(directions, firstSequence, firstSequence.length(), secondSequence.length());
    }

    private static TupleN<int[][]> lcsLength(String firstSequence, String secondSequence) {
        int firstLength = firstSequence.length();
        int secondLength = secondSequence.length();
        int[][] directions = new int[firstLength][secondLength];
        int[][] lcsLength = new int[firstLength + 1][secondLength + 1];

        for (int i = 1; i <= firstLength; i++) {
            for (int j = 1; j <= secondLength; j++) {
                if (firstSequence.charAt(i - 1) == secondSequence.charAt(j - 1)) {
                    lcsLength[i][j] = lcsLength[i - 1][j - 1] + 1;
                    directions[i - 1][j - 1] = Direction.NORTHWEST.ordinal();
                } else if (lcsLength[i - 1][j] >= lcsLength[i][j - 1]) {
                    lcsLength[i][j] = lcsLength[i - 1][j];
                    directions[i - 1][j - 1] = Direction.NORTH.ordinal();
                } else {
                    lcsLength[i][j] = lcsLength[i][j - 1];
                    directions[i - 1][j - 1] = Direction.WEST.ordinal();
                }
            }
        }

        return new TupleN<>(directions, lcsLength);
    }

    private static String getLcs(int[][] directions, String sequence, int i, int j) {
        if (i == 0 || j == 0) {
            return "";
        }

        if (directions[i - 1][j - 1] == Direction.NORTHWEST.ordinal()) {
            return getLcs(directions, sequence, i - 1, j - 1) + sequence.charAt(i - 1);
        }

        if (directions[i - 1][j - 1] == Direction.NORTH.ordinal()) {
            return getLcs(directions, sequence, i - 1, j);
        } else {
            return getLcs(directions, sequence, i, j - 1);
        }
    }

    private enum Direction {
        NORTH,
        NORTHWEST,
        WEST
    }
}