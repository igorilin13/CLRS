package igorilin13.com.github.main.parallel;

import java.util.stream.IntStream;

public class MatrixVectorMultiply {
    public static int[] compute(int[][] matrix, int[] vector) {
        int size = matrix.length;
        int[] res = new int[size];

        IntStream.range(0, size).parallel().forEach(i ->
                IntStream.range(0, size).parallel().forEach(j ->
                        res[i] += matrix[i][j] * vector[j]
                )
        );

        return res;
    }
}
