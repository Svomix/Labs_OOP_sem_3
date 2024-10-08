package operations;

import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TabulatedDifferentialOperatorTest
{
    @Test
    void deriveLinkedListTest()
    {
        TabulatedFunction list = new LinkedListTabulatedFunctionFactory().create(new double[]{1,2,3,4},new double[]{1,4,9,16});
        var difflistop = new TabulatedDifferentialOperator();
        TabulatedFunction listdiff = difflistop.derive(list);
        Assertions.assertEquals(7,listdiff.getY(2));
        Assertions.assertEquals(5,listdiff.getY(1));
    }
    @Test
    void deriveLinkedListFabricTest()
    {
        var listfac = new LinkedListTabulatedFunctionFactory();
        var difflistop = new TabulatedDifferentialOperator(listfac);
        TabulatedFunction listdiff = difflistop.derive(difflistop.getFactory().create(new double[]{1,2,3,4},new double[]{1,4,9,16}));
        Assertions.assertEquals(7,listdiff.getY(2));
        Assertions.assertEquals(5,listdiff.getY(1));
    }
    @Test
    void deriveLinkedArrayFabricTest()
    {
        var arrfac = new ArrayTabulatedFunctionFactory();
        var diffarrop = new TabulatedDifferentialOperator(arrfac);
        TabulatedFunction listdiff = diffarrop.derive(diffarrop.getFactory().create(new double[]{1,2,3,4},new double[]{1,4,9,16}));
        Assertions.assertEquals(7,listdiff.getY(2));
        Assertions.assertEquals(5,listdiff.getY(1));
    }
    @Test
    void deriveLinkedArrayTest()
    {
        TabulatedFunction arr = new LinkedListTabulatedFunctionFactory().create(new double[]{1,2,3,4},new double[]{1,4,9,16});
        var diffarrop = new TabulatedDifferentialOperator();
        TabulatedFunction listdiff = diffarrop.derive(arr);
        Assertions.assertEquals(7,listdiff.getY(2));
        Assertions.assertEquals(5,listdiff.getY(1));
    }
}
