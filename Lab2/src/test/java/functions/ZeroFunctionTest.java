package functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ZeroFunctionTest {
    private ZeroFunction func;

    @BeforeEach
    void setFunc() {
        func = new ZeroFunction();
    }

    @Test
    void applyTest1() {
        Assertions.assertEquals(0, func.apply(3.0));
    }

    @Test
    void applyTest2() {
        Assertions.assertEquals(0, func.apply(-3));
    }

    @Test
    void applyTest3() {
        Assertions.assertEquals(0, func.apply(0));
    }
}