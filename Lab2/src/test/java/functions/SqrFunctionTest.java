package functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SqrFunctionTest {
    private SqrFunction func;

    @BeforeEach
    void createFunction() {
        func = new SqrFunction();
    }


    @Test
    void applyTest1() {
        Assertions.assertEquals(4, func.apply(2));
    }

    @Test
    void applyTest2() {
        Assertions.assertEquals(100, func.apply(-10));
    }

    @Test
    void applyTest3() {
        Assertions.assertEquals(30.25, func.apply(5.5));
    }
}
