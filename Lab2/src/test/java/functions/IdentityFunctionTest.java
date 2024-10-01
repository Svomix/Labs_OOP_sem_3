package functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IdentityFunctionTest {
    private IdentityFunction func;

    @BeforeEach
    void createFunc() {
        func = new IdentityFunction();
    }

    @Test
    void applyTestInt() {
        double res = func.apply(2);
        Assertions.assertEquals(2, res);
    }

    @Test
    void applyTestDouble() {
        double res = func.apply(-10.3);
        Assertions.assertEquals(-10.3, res);
    }
}
