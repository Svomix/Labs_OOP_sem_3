package Labs_OOP_sem_3.operations;

import Labs_OOP_sem_3.functions.MathFunction;
import Labs_OOP_sem_3.functions.SqrFunction;
import Labs_OOP_sem_3.operations.LeftSteppingDifferentialOperator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LeftSteppingDifferentialOperatorTest {

    @Test
    void testLeftSteppingDifferentialOperator() {
        MathFunction function = new SqrFunction();
        LeftSteppingDifferentialOperator left = new LeftSteppingDifferentialOperator(0.5);
        Assertions.assertEquals(0, Double.compare(7.5, left.derive(function).apply(4)));
        Assertions.assertEquals(0, left.apply(1));
        Assertions.assertEquals(0.5, left.getStep());
        left.setStep(0.2);
        Assertions.assertEquals(0.2, left.getStep());
        Assertions.assertThrows(IllegalArgumentException.class, () -> new LeftSteppingDifferentialOperator(Double.NaN));
    }
    //(f(x)-f(x-h))/h

}