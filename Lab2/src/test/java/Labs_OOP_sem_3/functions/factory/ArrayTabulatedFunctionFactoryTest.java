package Labs_OOP_sem_3.functions.factory;

import Labs_OOP_sem_3.functions.ArrayTabulatedFunction;
import Labs_OOP_sem_3.functions.factory.ArrayTabulatedFunctionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArrayTabulatedFunctionFactoryTest {
    @Test
    void createArrayTest() {
        var arrf = new ArrayTabulatedFunctionFactory();
        Assertions.assertInstanceOf(ArrayTabulatedFunction.class, arrf.create(new double[]{1, 2, 3}, new double[]{1, 4, 9}));
    }
}
