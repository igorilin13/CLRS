package igorilin13.com.github.main.linear;

import igorilin13.com.github.main.util.ArrayUtils;

public class SimplexAlgorithm {
    private final double[][] A;
    private final double[] b;
    private final double[] c;

    private final int nonBasicSize;
    private final int basicSize;

    /**
     * cx -> max
     * Ax <= b
     * x >= 0
     */
    public SimplexAlgorithm(double[][] A, double[] b, double[] c) {
        this.A = ArrayUtils.copy(A);
        this.b = ArrayUtils.copy(b);
        this.c = ArrayUtils.copy(c);

        nonBasicSize = A[0].length;
        basicSize = A.length;
    }

    public double[] solve() {
        SimplexSlackForm slack = initSimplex();
        slack = iterateSimplex(slack);
        double[] solution = new double[nonBasicSize];
        for (int i = 0; i < nonBasicSize; i++) {
            if (slack.isBasic(i)) {
                solution[i] = slack.getB()[i];
            } else {
                solution[i] = 0;
            }
        }
        return solution;
    }

    public SimplexSlackForm initSimplex() {
        int minBInd = ArrayUtils.minIndex(b);
        if (b[minBInd] >= 0) {
            return new SimplexSlackForm(A, b, c);
        }

        int leaving = 2 * nonBasicSize + minBInd - 1;
        SimplexSlackForm auxSlack = createAuxSlack();
        auxSlack.pivot(leaving, nonBasicSize);
        auxSlack = iterateSimplex(auxSlack);

        int auxVarIndex = nonBasicSize;
        if (!auxSlack.isBasic(auxVarIndex) || auxSlack.getB()[auxVarIndex] == 0) {
            if (auxSlack.isBasic(auxVarIndex)) {
                auxSlack.pivot(auxVarIndex, auxSlack.getNonBasic()[0]);
            }
            return SimplexSlackForm.fromAux(auxSlack, auxVarIndex, c);
        } else {
            throw new IllegalArgumentException("Infeasible");
        }
    }

    private SimplexSlackForm createAuxSlack() {
        double[][] auxA = new double[basicSize][nonBasicSize + 1];
        for (int i = 0; i < basicSize; i++) {
            System.arraycopy(A[i], 0, auxA[i], 0, nonBasicSize);
            auxA[i][nonBasicSize] = -1;
        }
        double[] auxC = new double[nonBasicSize + 1];
        auxC[nonBasicSize] = -1;
        return new SimplexSlackForm(auxA, b, auxC);
    }

    private SimplexSlackForm iterateSimplex(SimplexSlackForm slack) {
        int entering = slack.findEntering();
        while (entering >= 0) {
            int leaving = slack.findLeaving(entering);
            slack.pivot(leaving, entering);
            entering = slack.findEntering();
        }
        return slack;
    }
}
