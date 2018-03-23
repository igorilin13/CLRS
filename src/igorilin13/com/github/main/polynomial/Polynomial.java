package igorilin13.com.github.main.polynomial;

import igorilin13.com.github.main.util.ListUtils;
import igorilin13.com.github.main.util.MathUtils;
import org.jetbrains.annotations.Nullable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public class Polynomial {
    private final List<Complex> coefficients;
    private final int degree;

    @Nullable
    private List<Complex> extendedDft;

    public Polynomial(List<Complex> coefficients) {
        int degree = coefficients.size();
        for (int i = coefficients.size() - 1; i >= 0 && coefficients.get(i).equals(Complex.ZERO); i--) {
            degree--;
        }
        this.degree = degree;

        this.coefficients = new ArrayList<>();
        for (int i = 0; i < degree; i++) {
            this.coefficients.add(coefficients.get(i));
        }
    }

    public Complex eval(Complex arg) {
        Complex currArg = Complex.ONE;
        Complex res = Complex.ZERO;
        for (Complex coeff : coefficients) {
            res = res.plus(coeff.multiply(currArg));
            currArg = currArg.multiply(arg);
        }
        return res;
    }

    public Polynomial plus(Polynomial oth) {
        return baseOp(oth, Complex::plus);
    }

    public Polynomial minus(Polynomial oth) {
        return baseOp(oth, Complex::minus);
    }

    private Polynomial baseOp(Polynomial oth, BiFunction<Complex, Complex, Complex> op) {
        int degree = Math.max(this.degree, oth.degree);
        List<Complex> res = new ArrayList<>(degree);
        for (int i = 0; i < degree; i++) {
            if (this.degree <= i) {
                res.add(oth.coefficients.get(i));
            } else if (oth.degree <= i) {
                res.add(this.coefficients.get(i));
            } else {
                res.add(op.apply(this.coefficients.get(i), oth.coefficients.get(i)));
            }
        }
        return new Polynomial(res);
    }

    public Polynomial multiply(Polynomial oth) {
        if (this.degree != oth.degree) {
            throw new NotImplementedException();
        }

        this.setExtendedDft();
        oth.setExtendedDft();

        List<Complex> multiplied = new ArrayList<>(2 * degree);
        for (int i = 0; i < this.extendedDft.size(); i++) {
            multiplied.add(this.extendedDft.get(i).multiply(oth.extendedDft.get(i)));
        }

        return new Polynomial(inverseDft(multiplied));
    }

    private void setExtendedDft() {
        if (extendedDft != null) {
            return;
        }
        List<Complex> extended = new ArrayList<>(2 * degree);
        extended.addAll(coefficients);
        for (int i = degree; i < 2 * degree; i++) {
            extended.add(Complex.ZERO);
        }
        extendedDft = toDft(extended);
    }

    public List<Complex> getCoefficients() {
        return Collections.unmodifiableList(coefficients);
    }

    private static List<Complex> toDft(List<Complex> a) {
        int degree = a.size();
        if (!MathUtils.isPowerOf2(degree)) {
            throw new IllegalArgumentException("Polynomial degree is supposed to be a power of 2");
        }
        if (degree == 1) {
            return Collections.singletonList(a.get(0));
        }
        Complex principalRoot = Complex.exp(2 * Math.PI / degree);
        Complex unityRoot = Complex.ONE;
        List<Complex> evenCoeffs = new ArrayList<>(degree / 2);
        List<Complex> oddCoeffs = new ArrayList<>(degree / 2);
        for (int i = 0; i < degree; i++) {
            if (i % 2 == 0) {
                evenCoeffs.add(a.get(i));
            } else {
                oddCoeffs.add(a.get(i));
            }
        }
        List<Complex> evenDft = toDft(evenCoeffs);
        List<Complex> oddDft = toDft(oddCoeffs);
        List<Complex> res = ListUtils.createWith(null, degree);
        for (int k = 0; k < degree / 2; k++) {
            Complex term = unityRoot.multiply(oddDft.get(k));
            res.set(k, evenDft.get(k).plus(term));
            res.set(k + degree / 2, evenDft.get(k).minus(term));
            unityRoot = unityRoot.multiply(principalRoot);
        }
        return res;
    }

    private static List<Complex> inverseDft(List<Complex> y) {
        List<Complex> dft = toDft(y);
        int degree = y.size();
        List<Complex> res = new ArrayList<>(degree);
        res.add(dft.get(0).divide(new Complex(degree, 0)));
        for (int i = 1; i < degree; i++) {
            res.add(dft.get(degree - i).divide(new Complex(degree, 0)));
        }
        return res;
    }
}
