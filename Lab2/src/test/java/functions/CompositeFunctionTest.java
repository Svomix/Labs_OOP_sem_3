package functions;

import functions.CompositeFunction;
import functions.IdentityFunction;
import functions.MathFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CompositeFunctionTest {
    private CompositeFunction cFunc;

    @Test
    void applyCompositeFunc() {
        FirstFunc func1 = new FirstFunc();
        SecondFunc func2 = new SecondFunc();
        cFunc = new CompositeFunction(func1, func2);
        double res = cFunc.apply(5);
        Assertions.assertEquals(Math.pow(Math.sin(5), 3), res);
    }

    @Test
    void applyCompositeFuncIdentify1() {
        FirstFunc func1 = new FirstFunc();
        IdentityFunction func2 = new IdentityFunction();
        cFunc = new CompositeFunction(func1, func2);
        double res = cFunc.apply(-2.5);
        Assertions.assertEquals(Math.sin(-2.5), res);
    }

    @Test
    void applyCompositeFuncIdentify2() {
        IdentityFunction func1 = new IdentityFunction();
        IdentityFunction func2 = new IdentityFunction();
        cFunc = new CompositeFunction(func1, func2);
        double res = cFunc.apply(.52);
        Assertions.assertEquals(.52, res);
    }

    @Test
    void applyCompositeFuncComposite() {
        FirstFunc func1 = new FirstFunc();
        CompositeFunction cfunc = new CompositeFunction(new SqrFunction(), new UnitFunction());
        CompositeFunction func = new CompositeFunction(func1, cfunc);
        double res = func.apply(10);
        Assertions.assertEquals(1, res);
    }
}


class FirstFunc implements MathFunction {
    @Override
    public double apply(double x) {
        return Math.sin(x);
    }
}

class SecondFunc implements MathFunction {
    @Override
    public double apply(double x) {
        return Math.pow(x, 3);
    }
}