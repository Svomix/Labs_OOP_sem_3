package functions.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TabulatedFunctionFactoryTest
{
    @Test
    void createStrictTest()
    {
        var arrfac = new ArrayTabulatedFunctionFactory();
        var arr = arrfac.createStrict(new double[]{1,2,3}, new double[]{1,4,9});
        Assertions.assertThrows(UnsupportedOperationException.class,()->arr.apply(2.5));
        Assertions.assertEquals(4,arr.apply(2));
        arr.setY(2,10);
        Assertions.assertEquals(10,arr.getY(2));
    }
    @Test
    void  createStrictUnmodifiableTest()
    {
        var listfac = new LinkedListTabulatedFunctionFactory();
        var list = listfac.createStrictUnmodifiable(new double[]{1,2,3}, new double[]{1,4,9});
        Assertions.assertEquals(1,list.apply(1));
        Assertions.assertThrows(UnsupportedOperationException.class,()->list.apply(10));
        Assertions.assertThrows(UnsupportedOperationException.class,()->list.setY(0,2));

    }
}
