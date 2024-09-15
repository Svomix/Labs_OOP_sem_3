package functions.mock;

import functions.AbstractTabulateFunction;
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
        Assertions.assertEquals(func.apply(-1), -1 + (-5 - -1) / (2.5 - 1) * (-1 - 1));
    }

    @Test
    void extrapolateRight() {
        Assertions.assertEquals(func.apply(5), -1 + (-5 - -1) / (2.5 - 1) * (5 - 1));
    }

    @Test
    void applyTest1() {
        Assertions.assertEquals(func.apply(1), -1);
    }

    @Test
    void applyTest2() {
        Assertions.assertEquals(func.apply(2.5), -5);
    }

    @Test
    void interpolateTest() {
        Assertions.assertEquals(func.interpolate(2, -6.4, 2.2, 13.3, 0), 13.3 + (0 - 13.3) / (2.2 - -6.4) * (2 - -6.4));
    }
}