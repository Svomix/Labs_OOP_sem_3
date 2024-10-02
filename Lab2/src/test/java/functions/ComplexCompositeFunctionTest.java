package functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ComplexCompositeFunctionTest {
    @Test
    public void test1() {
        MathFunction func = new CompositeFunction(new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{1, 2, 3}),
                new LinkedListTabulatedFunction(new double[]{1, 2, 3}, new double[]{1, 2, 3}));
        Assertions.assertEquals(func.apply(-50), -50);
    }
    @Test
    public void test2() {
        MathFunction func = new CompositeFunction(new ArrayTabulatedFunction(new double[]{1.7, 2.3, 3.8}, new double[]{-0.1, 2, 3.3}),
                new LinkedListTabulatedFunction(new double[]{1.2, 2.1, 5.5}, new double[]{0.3, 2.5, 3,7}));
        Assertions.assertEquals(func.apply(3.4), 2.6254901960784314);
        Assertions.assertEquals(func.apply(1), -8.866666666666667);
        Assertions.assertEquals(func.apply(1.7), -2.8777777777777778);
        Assertions.assertEquals(func.apply(-2), -34.53333333333334);
        Assertions.assertEquals(func.apply(22), 4.996078431372549);
    }

    @Test
    public void test3() {
        MathFunction func = new CompositeFunction(new ArrayTabulatedFunction(new double[]{1.7, 2.3, 3.8}, new double[]{-0.1, 2, 3.3}),
                new ArrayTabulatedFunction(new double[]{1.2, 2.1, 5.5}, new double[]{0.3, 2.5, 3,7}));
        Assertions.assertEquals(func.apply(3.4), 2.6254901960784314);
        Assertions.assertEquals(func.apply(1), -8.866666666666667);
        Assertions.assertEquals(func.apply(1.7), -2.8777777777777778);
        Assertions.assertEquals(func.apply(-2), -34.53333333333334);
        Assertions.assertEquals(func.apply(22), 4.996078431372549);
    }

    @Test
    public void test4() {
        MathFunction func = new CompositeFunction(new ArrayTabulatedFunction(new double[]{1.7, 2.3, 3.8}, new double[]{-0.1, 2, 3.3}),
                new RungeKuttaFunction(0.1, 1, 1, new DifferentialEquations((x, y) -> x * y)));
        Assertions.assertEquals(func.apply(3.4), 74.06184983420538);
        Assertions.assertEquals(func.apply(1), 0.06235263116377272);
        Assertions.assertEquals(func.apply(1.7), 1.7949905046470187);
        Assertions.assertEquals(func.apply(-2), 5.912144151766769E-37);
        Assertions.assertEquals(func.apply(22), 1.0359113127967836E79);
    }

    @Test
    public void test5() {
        MathFunction func = new CompositeFunction(new LinkedListTabulatedFunction(new double[]{1.7, 2.3, 3.8}, new double[]{-0.1, 2, 3.3}),
                new LinkedListTabulatedFunction(new double[]{1.2, 2.1, 5.5}, new double[]{0.3, 2.5, 3,7}));
        Assertions.assertEquals(func.apply(3.4), 2.6254901960784314);
        Assertions.assertEquals(func.apply(1), -8.866666666666667);
        Assertions.assertEquals(func.apply(1.7), -2.8777777777777778);
        Assertions.assertEquals(func.apply(-2), -34.53333333333334);
        Assertions.assertEquals(func.apply(22), 4.996078431372549);
    }
    @Test
    public void test6()
    {
        BSpline spline = new BSpline(new double[]{ 0, 0, 0, 1, 2, 3, 4, 4, 5, 5, 5 }, new double[]{1,0,0,2,2,4,5,3}, 2);
        MathFunction func = new CompositeFunction(new LinkedListTabulatedFunction(new double[]{1,2.5,3},new double[]{1,15.625,27}),new DeBoorFunction(spline));
        double acc = Math.abs(func.apply(1.2) - 1.9) / 1.9;
        Assertions.assertTrue(acc <= 0.2);
    }
    @Test
    public void test7()
    {
        MathFunction func = new CompositeFunction(new LinkedListTabulatedFunction(new double[]{1,2,3,4}, new double[]{1,4,9,16}),new SqrFunction());
        Assertions.assertEquals(81,func.apply(3));
        Assertions.assertEquals(156.25,func.apply(3.5));
        Assertions.assertEquals(3364,func.apply(10));
    }
    @Test
    public void test8()
    {
        MathFunction func = new CompositeFunction(new ArrayTabulatedFunction(new double[]{1,2.5,5.2,7,11.2},new double[]{1,1.58,2.23,2.28,3.34}),new IdentityFunction());
        Assertions.assertEquals(1.58,func.apply(2.5));
        Assertions.assertEquals(1.3866666666666667,func.apply(2));
        Assertions.assertEquals(3.541904761904762,func.apply(12));
    }
}
