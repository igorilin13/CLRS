package igorilin13.com.github.main.matrix;

public class InverseMatrix {
    public static double[][] compute(double[][] source) {
        int size = source.length;
        double[][] res = new double[size][size];
        double[] unitVector = new double[size];
        for (int i = 0; i < size; i++) {
            unitVector[i] = 1;
            if (i > 0) {
                unitVector[i - 1] = 0;
            }
            double[] resCol = LinearEquations.solve(source, unitVector);
            for (int k = 0; k < size; k++) {
                res[k][i] = resCol[k];
            }
        }
        return res;
    }
}
