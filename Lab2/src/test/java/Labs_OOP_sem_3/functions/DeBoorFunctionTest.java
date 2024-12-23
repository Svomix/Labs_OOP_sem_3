package Labs_OOP_sem_3.functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeBoorFunctionTest {
    @Test
    void DeBoorFunctionTest1() {
        double[] t = {0, 0, 0, 1, 2, 3, 4, 4, 5, 5, 5};
        double[] controlArr = {1, 0, 0, 2, 2, 4, 5, 3};
        BSpline spline = new BSpline(t, controlArr, 2);
        DeBoorFunction func = new DeBoorFunction(spline);
        double acc = Math.abs(func.apply(3) - 2) / 2;
        Assertions.assertTrue(acc <= 0.2);
    }

    @Test
    void DeBoorFunctionTest2() {
        double[] t = {0, 0, 0, 1, 2, 3, 4, 4, 5, 5, 5};
        double[] controlArr = {1, 0, 0, 2, 2, 4, 5, 3};
        BSpline spline = new BSpline(t, controlArr, 2);
        DeBoorFunction func = new DeBoorFunction(spline);
        double acc = Math.abs(func.apply(3.5) - 2.1) / 2.1;
        Assertions.assertTrue(acc <= 0.2);
    }

    @Test
    void DeBoorFunctionTest3() {
        double[] t = {0, 0, 0, 1, 2, 3, 4, 4, 5, 5, 5};
        double[] controlArr = {1, 0, 0, 2, 2, 4, 5, 3};
        BSpline spline = new BSpline(t, controlArr, 2);
        DeBoorFunction func = new DeBoorFunction(spline);
        double acc = Math.abs(func.apply(2.3) - 1.8) / 1.8;
        Assertions.assertTrue(acc <= 0.2);
    }

    double[] t = {0, 0, 0, 1, 2, 3, 4, 4, 5, 5, 5};
    double[] controlArr = {1, 0, 0, 2, 2, 4, 5, 3};
    BSpline b = new BSpline(t, controlArr, 2);

    @Test
    void getPTest() {
        Assertions.assertEquals(2, b.getP());
    }

    @Test
    void setPTest() {
        b.setP(3);
        Assertions.assertEquals(3, b.getP());
    }

    @Test
    void setAndGetTTest() {
        b.setT(new double[]{1, 2});
        Assertions.assertArrayEquals(new double[]{1, 2}, b.getT());
    }

    @Test
    void setAndGetControlTest() {
        b.setArrControl(new double[]{1, 2});
        Assertions.assertArrayEquals(new double[]{1, 2}, b.getArrControl());
    }
}