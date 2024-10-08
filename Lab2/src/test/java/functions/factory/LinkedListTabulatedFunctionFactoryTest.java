package functions.factory;

import functions.LinkedListTabulatedFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LinkedListTabulatedFunctionFactoryTest
{
    @Test
    void createListTest()
    {
        var listf = new LinkedListTabulatedFunctionFactory();
        Assertions.assertInstanceOf(LinkedListTabulatedFunction.class, listf.create(new double[]{1,4,9}, new double[]{1,2,3}));
    }
}
