package Labs_OOP_sem_3.functions.factory;

import Labs_OOP_sem_3.functions.ArrayTabulatedFunction;
import Labs_OOP_sem_3.functions.TabulatedFunction;

public class ArrayTabulatedFunctionFactory implements TabulatedFunctionFactory {
    @Override
    public TabulatedFunction create(double[] xValues, double[] yValues) {
        return new ArrayTabulatedFunction(xValues, yValues);
    }
}
