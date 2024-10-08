package operations;


import functions.MathFunction;
import functions.SqrFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RightSteppingDifferentialOperatorTest {

    @Test
    void testRightSteppingDifferentialOperator() {
        MathFunction mathFunction = new SqrFunction();
        RightSteppingDifferentialOperator right = new RightSteppingDifferentialOperator(0.5);
        Assertions.assertEquals(0, Double.compare(8.5, right.derive(mathFunction).apply(4)));
    }

    //(f(x+h)-f(x))/h
}