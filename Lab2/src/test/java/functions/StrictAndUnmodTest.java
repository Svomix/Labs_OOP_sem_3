package functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StrictAndUnmodTest {
    @Test
    void StrictWithUnmodTest() {
        double[] a = {0, 1, 2, 3, 4, 5, 6, 7};
        double[] b = {0, 1, 4, 9, 16, 25, 36, 49};
        var unmfunc = new UnmodifiableTabulatedFunction(new LinkedListTabulatedFunction(a, b));
        var strictfunc = new StrictTabulatedFunction(unmfunc);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> strictfunc.setY(5, 10));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> strictfunc.apply(6.5));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> strictfunc.apply(10));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> strictfunc.apply(-10));
        Assertions.assertEquals(5, strictfunc.getX(5));
        Assertions.assertEquals(7, strictfunc.rightBound());
        Assertions.assertEquals(0, strictfunc.leftBound());
    }

    @Test
    void UnmodWithStrictTest() {
        TabulatedFunction func = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        StrictTabulatedFunction wrapper1 = new StrictTabulatedFunction(func);
        UnmodifiableTabulatedFunction wrapper2 = new UnmodifiableTabulatedFunction(wrapper1);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> wrapper2.setY(5, 10));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> wrapper2.apply(6.5));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> wrapper2.apply(10));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> wrapper2.apply(-10));
        Assertions.assertEquals(5, wrapper2.getX(4));
        Assertions.assertEquals(5, wrapper2.rightBound());
        Assertions.assertEquals(1, wrapper2.leftBound());

    }
}
