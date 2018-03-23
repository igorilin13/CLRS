package igorilin13.com.github.test.polynomial;

import igorilin13.com.github.main.polynomial.Complex;
import igorilin13.com.github.main.polynomial.Polynomial;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static igorilin13.com.github.test.Assert.assertEquals;

public class PolynomialTest {
    private static final double PRECISION = 0.000001;

    private static final Polynomial POLYNOMIAL = new Polynomial(Arrays.asList(
            Complex.real(9),
            Complex.real(-10),
            Complex.real(7),
            Complex.real(6)
    ));

    @Test
    public void testMultiply() {
        List<Complex> factor = Arrays.asList(
                Complex.real(-5),
                Complex.real(4),
                Complex.real(0),
                Complex.real(-2)
        );

        List<Complex> expectedResult = Arrays.asList(
                Complex.real(-45),
                Complex.real(86),
                Complex.real(-75),
                Complex.real(-20),
                Complex.real(44),
                Complex.real(-14),
                Complex.real(-12)
        );

        assertEquals(expectedResult, POLYNOMIAL.multiply(new Polynomial(factor)).getCoefficients(), PRECISION);
    }

    @Test
    public void testEval() {
        assertEval(Complex.ZERO, Complex.real(9));

        assertEval(Complex.ONE, Complex.real(12));
        assertEval(Complex.real(1213), Complex.real(10718911044L));

        assertEval(Complex.real(-1), Complex.real(20));
        assertEval(Complex.real(-1213), Complex.real(-10698311860L));

        assertEval(new Complex(12, -13), new Complex(-26422, -22568));
    }

    private void assertEval(Complex arg, Complex expectedResult) {
        assertEquals(expectedResult, POLYNOMIAL.eval(arg), PRECISION);
    }

    @Test
    public void testPlus() {
        List<Complex> term = Arrays.asList(
                Complex.real(-4),
                Complex.real(1),
                Complex.real(-2),
                Complex.real(1),
                Complex.real(-6)
        );

        List<Complex> expectedResult = Arrays.asList(
                Complex.real(5),
                Complex.real(-9),
                Complex.real(5),
                Complex.real(7),
                Complex.real(-6)
        );

        assertEquals(expectedResult, POLYNOMIAL.plus(new Polynomial(term)).getCoefficients(), PRECISION);
    }

    @Test
    public void testMinus() {
        List<Complex> term = Arrays.asList(
                Complex.real(6),
                Complex.real(-5),
                Complex.real(-8),
                Complex.real(6)
        );

        List<Complex> expectedResult = Arrays.asList(
                Complex.real(3),
                Complex.real(-5),
                Complex.real(15)
        );

        assertEquals(expectedResult, POLYNOMIAL.minus(new Polynomial(term)).getCoefficients(), PRECISION);
    }
}
