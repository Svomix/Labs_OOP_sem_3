package functions;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RungeKuttaFunctionTest {
    private DifferentialEquations equations;

    @Test
    void test1() {
        equations = new DifferentialEquations((x, y) -> x * y);
        Assertions.assertEquals(5.501328749747154, new RungeKuttaFunction(0.1, 1, 1, equations).apply(2));
    }

    @Test
    void test2() {
        equations = new DifferentialEquations((x, y) -> Math.sin(Math.pow(x, y)));
        Assertions.assertEquals(1.1318024551809656, new RungeKuttaFunction(0.1, 1, 1, equations).apply(3));
    }

}