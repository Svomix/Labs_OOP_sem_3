package Labs_OOP_sem_3.functions;

public class SqrFunction implements MathFunction {

    @Override
    public double apply(double x) {
        return Math.pow(x, 2);
    }
}
