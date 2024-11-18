package Labs_OOP_sem_3.operations;

import Labs_OOP_sem_3.functions.MathFunction;

public interface DifferentialOperator<T> extends MathFunction {
    T derive(T function);
}
