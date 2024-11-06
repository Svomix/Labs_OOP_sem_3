package functions.factory;

import functions.TabulatedFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TabulatedFunctionFactoryTest {

    @Test
    void createUnmodifiableTabulatedFunctionList() {
        LinkedListTabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction function = factory.createUnmodifiable(new double[]{1, 2, 3, 5}, new double[]{1, 2, 3, 4});
        Assertions.assertThrows(UnsupportedOperationException.class, () -> function.setY(1, 1));
        Assertions.assertEquals(1, function.apply(1));
    }

    @Test
    void createUnmodifiableTabulatedFunctionArray() {
        var factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunction function = factory.createUnmodifiable(new double[]{1, 2, 3, 5}, new double[]{1, 2, 3, 4});
        Assertions.assertThrows(UnsupportedOperationException.class, () -> function.setY(1, 1));
        Assertions.assertEquals(1, function.apply(1));
    }

    @Test
    void createStrictTabulatedFunctionList() {
        LinkedListTabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction function = factory.createStrict(new double[]{1, 2, 3, 5}, new double[]{1, 2, 3, 4});
        Assertions.assertThrows(UnsupportedOperationException.class, () -> function.apply(2.5));
    }

    @Test
    void createStrictTabulatedFunctionArray() {
        var factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunction function = factory.createUnmodifiable(new double[]{1, 2, 3, 5}, new double[]{1, 2, 3, 4});
        Assertions.assertThrows(UnsupportedOperationException.class, () -> function.setY(1, 1));
    }

    @Test
    void createStrictUnmfTabulatedFunctionList() {
        LinkedListTabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction function = factory.createStrictUnmodifiable(new double[]{1, 2, 3, 5}, new double[]{1, 2, 3, 4});
        Assertions.assertThrows(UnsupportedOperationException.class, () -> function.apply(2.5));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> function.setY(1, 1));
    }

    @Test
    void createStrictUnmfTabulatedFunctionArray() {
        var factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunction function = factory.createStrictUnmodifiable(new double[]{1, 2, 3, 5}, new double[]{1, 2, 3, 4});
        Assertions.assertThrows(UnsupportedOperationException.class, () -> function.apply(2.5));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> function.setY(1, 1));
    }

}
