package Labs_OOP_sem_3.operations;


import Labs_OOP_sem_3.functions.MathFunction;
import Labs_OOP_sem_3.functions.SqrFunction;
import Labs_OOP_sem_3.operations.RightSteppingDifferentialOperator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RightSteppingDifferentialOperatorTest {

    @Test
    void testRightSteppingDifferentialOperator() {
        MathFunction mathFunction = new SqrFunction();
        RightSteppingDifferentialOperator right = new RightSteppingDifferentialOperator(0.5);
        Assertions.assertEquals(0, Double.compare(8.5, right.derive(mathFunction).apply(4)));
        Assertions.assertEquals(0, right.apply(1));
    }

    //(f(x+h)-f(x))/h
}