package functions.factory;

import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;
import functions.UnmodifiableTabulatedFunction;

public interface TabulatedFunctionFactory
{
    TabulatedFunction create(double[] xValues, double[] yValues);
    default TabulatedFunction createUnmodifiable (double[] xValues, double[] yValues){
        return new UnmodifiableTabulatedFunction(new ArrayTabulatedFunction(xValues, yValues));
    }
}
