package functions;

public class CompositeFunction implements MathFunction
{
    final private MathFunction firstFunction;
    final private MathFunction secondFunction;
    public CompositeFunction(MathFunction firstf, MathFunction secondf) // правильный порядок функций
    {
        firstFunction = firstf;
        secondFunction = secondf;
    }
    @Override
    public double apply(double x)
    {
        return secondFunction.apply(firstFunction.apply(x));
    }
}