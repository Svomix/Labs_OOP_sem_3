package Labs_OOP_sem_3.functions;

import Labs_OOP_sem_3.annotations.SimpleFunctionAnnotation;

@SimpleFunctionAnnotation(name = "Квадратичная функция",priority = 4)
public class SqrFunction implements MathFunction {

    @Override
    public double apply(double x) {
        return Math.pow(x, 2);
    }
}
