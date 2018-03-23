package igorilin13.com.github.main.parallel;

import java.util.stream.IntStream;

public class SquareMatrixMultiply {
    public static int[][] compute(int[][] first, int[][] second) {
        int size = first.length;
        int[][] res = new int[size][size];

        IntStream.range(0, size).parallel().forEach(i -> {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    res[i][j] += first[i][k] * second[i][j];
                }
            }
        });

        return res;
    }
}
