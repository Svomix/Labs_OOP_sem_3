package functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class MathFunctionTest {
    private MathFunction h;
    private MathFunction g;
    private MathFunction f;

    @Test
    void andThenTest1() {
        h = new FirstFunc();
        g = new ConstantFunction(1);
        f = new IdentityFunction();
        Assertions.assertEquals(h.apply(g.apply(f.apply(5))), h.andThen(g).andThen(f).apply(5));
    }

    @Test
    void andThenTest2() {
        h = new SqrFunction();
        g = new UnitFunction();
        f = new ZeroFunction().andThen(new ConstantFunction(3));
        Assertions.assertEquals(h.apply(g.apply(f.apply(1))), h.andThen(g).andThen(f).apply(1));
    }

    @Test
    void andThenTest3() {
        h = new UnitFunction().andThen(new SecondFunc());
        g = new ConstantFunction(1);
        f = new IdentityFunction();
        Assertions.assertEquals(h.apply(g.apply(f.apply(0))), h.andThen(g).andThen(f).apply(0));
    }
}