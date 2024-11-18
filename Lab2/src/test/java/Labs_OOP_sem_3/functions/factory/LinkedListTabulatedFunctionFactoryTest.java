package Labs_OOP_sem_3.functions.factory;

import Labs_OOP_sem_3.functions.LinkedListTabulatedFunction;
import Labs_OOP_sem_3.functions.factory.LinkedListTabulatedFunctionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LinkedListTabulatedFunctionFactoryTest {
    @Test
    void createListTest() {
        var listf = new LinkedListTabulatedFunctionFactory();
        Assertions.assertInstanceOf(LinkedListTabulatedFunction.class, listf.create(new double[]{1, 4, 9}, new double[]{1, 2, 3}));
    }
}
