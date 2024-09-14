package functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class UnitFunctionTest {
    private UnitFunction func;

    @BeforeEach
    void createFunction() {
        func = new UnitFunction();
    }

    @Test
    void applyTest1() {
        Assertions.assertEquals(func.apply(4), 1);
    }

    @Test
    void applyTest2() {
        Assertions.assertEquals(func.apply(7), 1);
    }

    @Test
    void applyTest3() {
        Assertions.assertEquals(func.apply(1), 1);
    }
}