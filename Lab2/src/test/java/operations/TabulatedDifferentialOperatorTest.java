package operations;

import concurrent.SynchronizedTabulatedFunction;
import functions.ArrayTabulatedFunction;
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
        TabulatedFunction arrdiff = diffarrop.derive(diffarrop.getFactory().create(new double[]{1,2,3,4},new double[]{1,4,9,16}));
        Assertions.assertInstanceOf(ArrayTabulatedFunction.class, arrdiff);
        Assertions.assertEquals(7,arrdiff.getY(2));
        Assertions.assertEquals(5,arrdiff.getY(1));
    }
    @Test
    void deriveLinkedArrayTest()
    {
        var fac = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction arr = fac.create(new double[]{1,2,3,4},new double[]{1,4,9,16});
        var diffarrop = new TabulatedDifferentialOperator(fac);
        TabulatedFunction listdiff = diffarrop.derive(arr);
        Assertions.assertInstanceOf(LinkedListTabulatedFunction.class, listdiff);
        Assertions.assertEquals(7,listdiff.getY(2));
        Assertions.assertEquals(5,listdiff.getY(1));
    }
    @Test
    void applyTest()
    {
        var diff = new TabulatedDifferentialOperator();
        Assertions.assertThrows(UnsupportedOperationException.class,()->diff.apply(2));
    }
    @Test
    void setest()
    {
        var diff = new TabulatedDifferentialOperator();
        var fac = new LinkedListTabulatedFunctionFactory();
        diff.setFactory(fac);
        Assertions.assertEquals(fac,diff.getFactory());
    }
    @Test
    void deriveSync(){
        TabulatedFunction arr = new ArrayTabulatedFunction(new double[]{1,2,3,4},new double[]{1,4,9,16});
        TabulatedDifferentialOperator diff = new TabulatedDifferentialOperator();
        TabulatedFunction list = diff.derive(arr);
        Assertions.assertEquals(7,list.getY(2));
        Assertions.assertEquals(5,list.getY(1));
    }

    @Test
    void deriveSync2(){
        SynchronizedTabulatedFunction arr = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(new double[]{1,2,3,4},new double[]{1,4,9,16}));
        TabulatedDifferentialOperator diff = new TabulatedDifferentialOperator();
        TabulatedFunction list = diff.derive(arr);
        Assertions.assertEquals(7,list.getY(2));
        Assertions.assertEquals(5,list.getY(1));
    }
    @Test
    void deriveSync3(){
        SynchronizedTabulatedFunction arr = new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(new double[]{1,2,3,4},new double[]{1,4,9,16}));
        TabulatedDifferentialOperator diff = new TabulatedDifferentialOperator();
        TabulatedFunction list = diff.derive(arr);
        Assertions.assertEquals(7,list.getY(2));
        Assertions.assertEquals(5,list.getY(1));
    }

    @Test
    void deriveSync4(){
        TabulatedFunction arr = new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(new double[]{1,2,3,4},new double[]{1,4,9,16}));
        TabulatedDifferentialOperator diff = new TabulatedDifferentialOperator();
        TabulatedFunction list = diff.derive(arr);
        Assertions.assertEquals(7,list.getY(2));
        Assertions.assertEquals(5,list.getY(1));
    }
}
