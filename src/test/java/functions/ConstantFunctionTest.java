package functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ConstantFunctionTest {
    private ConstantFunction func;

    @BeforeEach
    void createFunction() {
        func = new ConstantFunction(3);
    }

    @Test
    void applyTest1() {
        Assertions.assertEquals(func.apply(5), 3);
    }

    @Test
    void applyTest2() {
        Assertions.assertEquals(func.apply(3), 3);
    }

    @Test
    void applyTest3() {
        Assertions.assertEquals(func.apply(0), 3);
    }
}