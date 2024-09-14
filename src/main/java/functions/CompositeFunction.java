package functions;

public class CompositeFunction implements MathFunction
{
    private MathFunction firstFunction;
    private MathFunction secondFunction;
    public CompositeFunction(MathFunction firstf, MathFunction secondf)
    {
        firstFunction = firstf;
        secondFunction = secondf;
    }
    @Override
    public double apply(double x)
    {
        return firstFunction.apply(secondFunction.apply(x));
    }
}