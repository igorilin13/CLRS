package igorilin13.com.github.main.matrix;

import igorilin13.com.github.main.util.ArrayUtils;
import igorilin13.com.github.main.util.TupleN;

/*
 * Ax = b
 */
public class LinearEquations {
    public static double[] solve(double[][] sourceA, double[] b) {
        int size = sourceA.length;
        double[][] A = ArrayUtils.copy(sourceA);
        int[] permutation = decompose(A);
        double[][] lower = new double[size][size];
        double[][] upper = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i > j) {
                    lower[i][j] = A[i][j];
                } else {
                    if (i == j) {
                        lower[i][j] = 1;
                    }
                    upper[i][j] = A[i][j];
                }
            }
        }
        return solve(lower, upper, permutation, b);
    }

    private static int[] decompose(double[][] A) {
        int size = A.length;
        int[] permutation = new int[size];
        for (int i = 0; i < size; i++) {
            permutation[i] = i;
        }
        for (int k = 0; k < size; k++) {
            double max = 0;
            int maxIndex = k;
            for (int i = k; i < size; i++) {
                double abs = Math.abs(A[i][k]);
                if (abs > max) {
                    max = abs;
                    maxIndex = i;
                }
            }
            if (max == 0) {
                throw new IllegalArgumentException("Singular matrix");
            }
            int swap = permutation[k];
            permutation[k] = permutation[maxIndex];
            permutation[maxIndex] = swap;
            for (int i = 0; i < size; i++) {
                double swapDouble = A[k][i];
                A[k][i] = A[maxIndex][i];
                A[maxIndex][i] = swapDouble;
            }
            for (int i = k + 1; i < size; i++) {
                A[i][k] = A[i][k] / A[k][k];
                for (int j = k + 1; j < size; j++) {
                    A[i][j] -= A[i][k] * A[k][j];
                }
            }
        }
        return permutation;
    }

    private static double[] solve(double[][] lower, double[][] upper, int[] permutation, double[] b) {
        int size = lower.length;
        double[] x = new double[size];
        double[] y = new double[size];
        for (int i = 0; i < size; i++) {
            double sum = 0;
            for (int j = 0; j < i; j++) {
                sum += lower[i][j] * y[j];
            }
            y[i] = b[permutation[i]] - sum;
        }
        for (int i = size - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i; j < size; j++) {
                sum += upper[i][j] * x[j];
            }
            x[i] = (y[i] - sum) / upper[i][i];
        }
        return x;
    }

    public static TupleN<double[][]> luDecompose(double[][] sourceA) {
        int size = sourceA.length;
        double[][] A = ArrayUtils.copy(sourceA);
        double[][] lower = new double[size][size];
        double[][] upper = new double[size][size];
        for (int i = 0; i < size; i++) {
            lower[i][i] = 1;
        }
        for (int k = 0; k < size; k++) {
            upper[k][k] = A[k][k];
            for (int i = k; i < size; i++) {
                lower[i][k] = A[i][k] / upper[k][k];
                upper[k][i] = A[k][i];
            }
            for (int i = k; i < size; i++) {
                for (int j = k; j < size; j++) {
                    A[i][j] -= lower[i][k] * upper[k][j];
                }
            }
        }
        return new TupleN<>(lower, upper);
    }
}

