package Labs_OOP_sem_3.functions;

import Labs_OOP_sem_3.annotations.SimpleFunctionAnnotation;

@SimpleFunctionAnnotation(name = "Тождественная функция", priority = 3)
public class IdentityFunction implements MathFunction {
    @Override
    public double apply(double x) {
        return x;
    }
}