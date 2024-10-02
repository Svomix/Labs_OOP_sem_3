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
        Assertions.assertEquals(3, func.apply(5));
    }

    @Test
    void applyTest2() {
        Assertions.assertEquals(3, func.apply(3));
    }

    @Test
    void applyTest3() {
        Assertions.assertEquals(3, func.apply(0));
    }
}