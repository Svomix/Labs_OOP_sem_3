package Labs_OOP_sem_3.functions.factory;

import Labs_OOP_sem_3.functions.ArrayTabulatedFunction;
import Labs_OOP_sem_3.functions.StrictTabulatedFunction;
import Labs_OOP_sem_3.functions.TabulatedFunction;
import Labs_OOP_sem_3.functions.UnmodifiableTabulatedFunction;

public interface TabulatedFunctionFactory {
    TabulatedFunction create(double[] xValues, double[] yValues);

    default TabulatedFunction createStrict(double[] xValues, double[] yValues) {
        return new StrictTabulatedFunction(create(xValues, yValues));
    }

    default TabulatedFunction createStrictUnmodifiable(double[] xValues, double[] yValues) {
        return new UnmodifiableTabulatedFunction(new StrictTabulatedFunction(create(xValues, yValues)));
    }

    default TabulatedFunction createUnmodifiable(double[] xValues, double[] yValues) {
        return new UnmodifiableTabulatedFunction(new ArrayTabulatedFunction(xValues, yValues));
    }
}