package Labs_OOP_sem_3.functions;


import Labs_OOP_sem_3.exceptions.ArrayIsNotSortedException;
import Labs_OOP_sem_3.exceptions.DifferentLengthOfArraysException;
import Labs_OOP_sem_3.functions.AbstractTabulateFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AbstractTabulateFunctionTest {

    @Test
    void checkLengthIsTheSameTest1() {
        Assertions.assertThrows(DifferentLengthOfArraysException.class, () -> AbstractTabulateFunction.checkLengthIsTheSame(new double[]{1, 2, 3}, new double[]{4, 5}));
    }

    @Test
    void checkLengthIsTheSameTest2() {
        Assertions.assertThrows(DifferentLengthOfArraysException.class, () -> AbstractTabulateFunction.checkLengthIsTheSame(new double[]{1, 2}, new double[]{4, 5, 6}));
    }

    @Test
    void checkLengthIsTheSameTest3() {
        Assertions.assertDoesNotThrow(() -> AbstractTabulateFunction.checkLengthIsTheSame(new double[]{1, 2, 3}, new double[]{4, 5, 6}));
    }

    @Test
    void checkSorted() {
        Assertions.assertDoesNotThrow(() -> AbstractTabulateFunction.checkSorted(new double[]{1, 2, 3}));
        Assertions.assertThrows(ArrayIsNotSortedException.class, () -> AbstractTabulateFunction.checkSorted(new double[]{5, 4, 6}));
    }
}