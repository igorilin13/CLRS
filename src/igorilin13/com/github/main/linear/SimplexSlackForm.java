package igorilin13.com.github.main.linear;

import igorilin13.com.github.main.util.ArrayUtils;

/**
 * z = v + cx -> max
 * x = b - Ax
 */
public class SimplexSlackForm {
    private static final int POS_INF = Integer.MAX_VALUE;

    private int[] nonBasic;
    private int[] basic;
    private double[][] A;
    private double[] b;
    private double[] c;
    private double v;

    private SimplexSlackForm(SimplexSlackForm slackForm) {
        nonBasic = slackForm.nonBasic;
        basic = slackForm.basic;
        A = slackForm.A;
        b = slackForm.b;
        c = slackForm.c;
        v = slackForm.v;
    }

    /**
     * z = cx -> max
     * Ax <= b
     * x >= 0
     */
    SimplexSlackForm(double[][] A, double[] b, double[] c) {
        int basicSize = A.length;
        int nonBasicSize = A[0].length;
        int size = basicSize + nonBasicSize;

        nonBasic = new int[nonBasicSize];
        for (int i = 0; i < nonBasicSize; i++) {
            nonBasic[i] = i;
        }

        basic = new int[basicSize];
        for (int i = nonBasicSize; i < size; i++) {
            basic[i - nonBasicSize] = i;
        }

        this.A = new double[size][size];
        for (int i = 0; i < basicSize; i++) {
            System.arraycopy(A[i], 0, this.A[basic[i]], 0, nonBasicSize);
        }

        this.b = new double[size];
        System.arraycopy(b, 0, this.b, nonBasicSize, basicSize);

        this.c = new double[size];
        System.arraycopy(c, 0, this.c, 0, nonBasicSize);
    }

    private SimplexSlackForm(int basicSize, int nonBasicSize) {
        setEmpty(basicSize, nonBasicSize);
    }

    private void setEmpty(int basicSize, int nonBasicSize) {
        nonBasic = new int[nonBasicSize];
        basic = new int[basicSize];
        int size = basicSize + nonBasicSize;
        A = new double[size][size];
        b = new double[size];
        c = new double[size];
    }

    int findEntering() {
        for (int j : nonBasic) {
            if (c[j] > 0) {
                return j;
            }
        }
        return -1;
    }

    boolean isBasic(int index) {
        return ArrayUtils.contains(basic, index);
    }

    int findLeaving(int entering) {
        double[] delta = new double[A[0].length];
        for (int i : basic) {
            if (A[i][entering] > 0) {
                delta[i] = b[i] / A[i][entering];
            } else {
                delta[i] = POS_INF;
            }
        }
        int result = -1;
        for (int i : basic) {
            if (result < 0 || delta[result] > delta[i]) {
                result = i;
            }
        }
        if (delta[result] == POS_INF) {
            throw new IllegalArgumentException("Unbounded");
        }
        return result;
    }

    void pivot(int leaving, int entering) {
        int basicSize = basic.length;
        int nonBasicSize = nonBasic.length;

        SimplexSlackForm oldSlack = new SimplexSlackForm(this);
        setEmpty(basicSize, nonBasicSize);

        b[entering] = oldSlack.b[leaving] / oldSlack.A[leaving][entering];
        for (int j : oldSlack.nonBasic) {
            if (j != entering) {
                A[entering][j] = oldSlack.A[leaving][j] / oldSlack.A[leaving][entering];
            }
        }
        A[entering][leaving] = 1 / oldSlack.A[leaving][entering];

        for (int i : oldSlack.basic) {
            if (i != leaving) {
                b[i] = oldSlack.b[i] - oldSlack.A[i][entering] * b[entering];
                for (int j : oldSlack.nonBasic) {
                    if (j != entering) {
                        A[i][j] = oldSlack.A[i][j] - oldSlack.A[i][entering] * A[entering][j];
                    }
                }
                A[i][leaving] = -oldSlack.A[i][entering] * A[entering][leaving];
            }
        }

        v = oldSlack.v + oldSlack.c[entering] * b[entering];
        for (int j : oldSlack.nonBasic) {
            if (j != entering) {
                c[j] = oldSlack.c[j] - oldSlack.c[entering] * A[entering][j];
            }
        }
        c[leaving] = -oldSlack.c[entering] * A[entering][leaving];

        System.arraycopy(oldSlack.nonBasic, 0, nonBasic, 0, nonBasicSize);
        for (int i = 0; i < nonBasicSize; i++) {
            if (nonBasic[i] == entering) {
                nonBasic[i] = leaving;
                break;
            }
        }

        System.arraycopy(oldSlack.basic, 0, basic, 0, basicSize);
        for (int i = 0; i < basicSize; i++) {
            if (basic[i] == leaving) {
                basic[i] = entering;
                break;
            }
        }
    }

    public int[] getNonBasic() {
        return ArrayUtils.copy(nonBasic);
    }

    public int[] getBasic() {
        return ArrayUtils.copy(basic);
    }

    public double[][] getA() {
        return ArrayUtils.copy(A);
    }

    public double[] getB() {
        return ArrayUtils.copy(b);
    }

    public double[] getC() {
        return ArrayUtils.copy(c);
    }

    public double getV() {
        return v;
    }

    static SimplexSlackForm fromAux(SimplexSlackForm auxSlack, int auxVarIndex, double[] c) {
        int basicSize = auxSlack.basic.length;
        int nonBasicSize = auxSlack.nonBasic.length - 1;
        int size = basicSize + nonBasicSize;
        SimplexSlackForm resSlack = new SimplexSlackForm(basicSize, nonBasicSize);

        for (int i = 0; i < size; i++) {
            int auxIndex = i < nonBasicSize ? i : i + 1;
            resSlack.b[i] = auxSlack.b[auxIndex];
            for (int j = 0; j < size; j++) {
                resSlack.A[i][j] = auxSlack.A[auxIndex][j < nonBasicSize ? j : j + 1];
            }
        }

        arrayFromAux(auxSlack.basic, resSlack.basic, auxVarIndex);
        arrayFromAux(auxSlack.nonBasic, resSlack.nonBasic, auxVarIndex);

        for (int i = 0; i < nonBasicSize; i++) {
            if (auxSlack.isBasic(i)) {
                resSlack.v += c[i] * auxSlack.b[i];
                for (int j = 0; j < size; j++) {
                    resSlack.c[j] += -1 * c[i] * resSlack.A[i][j];
                }
            } else {
                resSlack.c[i] += c[i];
            }
        }
        return resSlack;
    }

    private static void arrayFromAux(int[] source, int[] dest, int auxVarInd) {
        int currentIndex = 0;
        for (int value : source) {
            if (value != auxVarInd) {
                dest[currentIndex] = value;
                if (dest[currentIndex] > auxVarInd) {
                    dest[currentIndex]--;
                }
                currentIndex++;
            }
        }
    }
}