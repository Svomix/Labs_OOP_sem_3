package Labs_OOP_sem_3.functions.mock;

import Labs_OOP_sem_3.functions.mock.MockTabulatedFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class MockTabulatedFunctionTest {

    private MockTabulatedFunction func;

    @BeforeEach
    public void createFunction() {
        func = new MockTabulatedFunction();
    }

    @Test
    void extrapolateLeft() {
        Assertions.assertEquals(-1 + (-5 - -1) / (2.5 - 1) * (-1 - 1), func.apply(-1));
    }

    @Test
    void extrapolateRight() {
        Assertions.assertEquals(-1 + (-5 - -1) / (2.5 - 1) * (5 - 1), func.apply(5));
    }

    @Test
    void applyTest1() {
        Assertions.assertEquals(-1, func.apply(1));
    }

    @Test
    void applyTest2() {
        Assertions.assertEquals(-5, func.apply(2.5));
    }

    @Test
    void interpolateTest() {
        Assertions.assertEquals(13.3 + (0 - 13.3) / (2.2 - -6.4) * (2 - -6.4), func.interpolate(2, -6.4, 2.2, 13.3, 0));
    }

    @Test
    void getY() {
        Assertions.assertEquals(-1, func.getY(0));
    }

    @Test
    void setY() {
        func.setY(1, 2.0);
    }

    @Test
    void getX() {
        Assertions.assertEquals(1, func.getX(0));
    }

    @Test
    void indexOfX() {
        Assertions.assertEquals(0, func.indexOfX(1));
    }

    @Test
    void indexOfY() {
        Assertions.assertEquals(0, func.indexOfY(-1));
    }

    @Test
    void leftBound() {
        Assertions.assertEquals(1, func.leftBound());
    }

    @Test
    void rightBound() {
        Assertions.assertEquals(2.5, func.rightBound());
    }

    @Test
    void floorIndexOfX() {
        Assertions.assertEquals(func.getCount(), func.floorIndexOfX(3));
    }

    @Test
    void floorIndexOfXTest() {
        MockTabulatedFunction func = new MockTabulatedFunction();
        Assertions.assertEquals(func.getCount(), func.floorIndexOfX(6));
    }

}