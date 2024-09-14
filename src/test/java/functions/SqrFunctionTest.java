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
        double result = func.apply(2);
        Assertions.assertEquals(4, result);
    }

    @Test
    void applyTest2() {
        double result = func.apply(-10);
        Assertions.assertEquals(100, result);
    }

    @Test
    void applyTest3() {
        double result = func.apply(5.5);
        Assertions.assertEquals(30.25, result);
    }
}
