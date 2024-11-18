package Labs_OOP_sem_3.functions;

public class ConstantFunction implements MathFunction {
    private final double constant;

    public ConstantFunction(double constant) {
        this.constant = constant;
    }


    public double getConstant() {
        return constant;
    }

    @Override
    public double apply(double x) {
        return constant;
    }
}
