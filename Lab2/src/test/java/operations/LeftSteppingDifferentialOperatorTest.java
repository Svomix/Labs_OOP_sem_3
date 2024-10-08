package operations;

import functions.MathFunction;
import functions.SqrFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LeftSteppingDifferentialOperatorTest {

    @Test
    void testLeftSteppingDifferentialOperator() {
        MathFunction function = new SqrFunction();
        LeftSteppingDifferentialOperator left = new LeftSteppingDifferentialOperator(0.5);
        Assertions.assertEquals(0, Double.compare(7.5, left.derive(function).apply(4)));
    }
    //(f(x)-f(x-h))/h

}