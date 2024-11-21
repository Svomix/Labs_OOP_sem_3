package Labs_OOP_sem_3.operations;


import Labs_OOP_sem_3.functions.MathFunction;
import Labs_OOP_sem_3.functions.SqrFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MiddleSteppingDifferentialOperatorTest {

    @Test
    void test1() {
        MathFunction mathFunction = new SqrFunction();
        MiddleSteppingDifferentialOperator middle = new MiddleSteppingDifferentialOperator(0.5);
        Assertions.assertEquals(0, Double.compare(6, middle.derive(mathFunction).apply(3)));
        Assertions.assertEquals(0, middle.apply(1));
    }
    // x^2 (f(x + h) - f(x - h) / 2h)
    // f(3 + 0.5) - f(3 - 0.5) / 1)

}