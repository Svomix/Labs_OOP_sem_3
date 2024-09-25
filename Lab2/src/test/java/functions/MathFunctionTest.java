package functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class MathFunctionTest {
    private MathFunction h;
    private MathFunction g;
    private MathFunction f;

    @Test
    void andThenTest1() {
        f = new FirstFunc();
        g = new ConstantFunction(1);
        h = new IdentityFunction();
        Assertions.assertEquals(h.apply(g.apply(f.apply(5))), f.andThen(g).andThen(h).apply(5));
    }

    @Test
    void andThenTest2() {
        f = new SqrFunction();
        g = new UnitFunction();
        h = new ZeroFunction().andThen(new ConstantFunction(3));
        Assertions.assertEquals(h.apply(g.apply(f.apply(1))), f.andThen(g).andThen(h).apply(1));
    }

    @Test
    void andThenTest3() {
        f = new UnitFunction().andThen(new SecondFunc());
        g = new ConstantFunction(1);
        h = new IdentityFunction();
        Assertions.assertEquals(h.apply(g.apply(f.apply(0))), f.andThen(g).andThen(h).apply(0));
    }
}