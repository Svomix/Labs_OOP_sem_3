package operations;


import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TabulatedFunctionOperationServiceTest {
    @Test
    void test() {
        TabulatedFunction function = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});

    }

}