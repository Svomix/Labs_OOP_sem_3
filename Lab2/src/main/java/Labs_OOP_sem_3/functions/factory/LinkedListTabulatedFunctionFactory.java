package Labs_OOP_sem_3.functions.factory;

import Labs_OOP_sem_3.functions.LinkedListTabulatedFunction;
import Labs_OOP_sem_3.functions.TabulatedFunction;

public class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory {
    @Override
    public TabulatedFunction create(double[] xValues, double[] yValues) {
        return new LinkedListTabulatedFunction(xValues, yValues);
    }
}
