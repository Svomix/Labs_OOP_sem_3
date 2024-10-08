package functions.factory;
import functions.TabulatedFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TabulatedFunctionFactoryTest {

    @Test
    void createUnmodifiableTabulatedFunction() {
        LinkedListTabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction function = factory.createUnmodifiable(new double[]{1, 2, 3, 5}, new double[] {1, 2, 3, 4});
        Assertions.assertThrows(UnsupportedOperationException.class,() -> function.setY(1, 1));
        Assertions.assertAll(() -> function.apply(1));
    }
}