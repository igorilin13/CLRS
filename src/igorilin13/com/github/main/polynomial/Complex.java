package igorilin13.com.github.main.polynomial;

import igorilin13.com.github.main.util.MathUtils;

public class Complex {
    private static final double EQUALS_PRECISION = 0.000001;

    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);

    private final double re;
    private final double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public Complex plus(Complex oth) {
        double re = this.re + oth.re;
        double im = this.im + oth.im;
        return new Complex(re, im);
    }

    public Complex minus(Complex oth) {
        double re = this.re - oth.re;
        double im = this.im - oth.im;
        return new Complex(re, im);
    }

    public Complex multiply(Complex oth) {
        double re = this.re * oth.re - this.im * oth.im;
        double im = this.re * oth.im + this.im * oth.re;
        return new Complex(re, im);
    }

    public Complex divide(Complex oth) {
        return this.multiply(oth.reciprocal());
    }

    private Complex reciprocal() {
        double denom = re * re + im * im;
        return new Complex(this.re / denom, -this.im / denom);
    }

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    public static Complex real(double re) {
        return new Complex(re, 0);
    }

    public static Complex exp(double degree) {
        return new Complex(Math.cos(degree), Math.sin(degree));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Complex complex = (Complex) o;

        return MathUtils.isEquals(complex.re, re, EQUALS_PRECISION)
                && MathUtils.isEquals(complex.im, im, EQUALS_PRECISION);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(re);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(im);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "(" + re + ", " + im + ")";
    }
}
