package functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ComplexCompositeFunctionTest {
    @Test
    public void test() {
        MathFunction func = new CompositeFunction(new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{1, 2, 3}),
                new LinkedListTabulatedFunction(new double[]{1, 2, 3}, new double[]{1, 2, 3}));
        Assertions.assertEquals(func.apply(-50), -50);
    }
    @Test
    public void test2() {
        MathFunction func = new CompositeFunction(new ArrayTabulatedFunction(new double[]{1.7, 2.3, 3.8}, new double[]{-0.1, 2, 3.3}),
                new LinkedListTabulatedFunction(new double[]{1.2, 2.1, 5.5}, new double[]{0.3, 2.5, 3,7}));
        Assertions.assertEquals(func.apply(3.4), 4.585925925925925);
        Assertions.assertEquals(func.apply(1), -8.866666666666667);
        Assertions.assertEquals(func.apply(1.7), -2.8777777777777778);
        Assertions.assertEquals(func.apply(-2), -34.53333333333334);
        Assertions.assertEquals(func.apply(22), 43.99037037037036);
    }

    @Test
    public void test3() {
        MathFunction func = new CompositeFunction(new ArrayTabulatedFunction(new double[]{1.7, 2.3, 3.8}, new double[]{-0.1, 2, 3.3}),
                new ArrayTabulatedFunction(new double[]{1.2, 2.1, 5.5}, new double[]{0.3, 2.5, 3,7}));
        Assertions.assertEquals(func.apply(3.4), 4.585925925925925);
        Assertions.assertEquals(func.apply(1), -8.866666666666667);
        Assertions.assertEquals(func.apply(1.7), -2.8777777777777778);
        Assertions.assertEquals(func.apply(-2), -34.53333333333334);
        Assertions.assertEquals(func.apply(22), 43.99037037037036);
    }

}
