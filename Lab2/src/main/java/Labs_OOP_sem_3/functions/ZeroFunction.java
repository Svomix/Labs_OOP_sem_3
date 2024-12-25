package Labs_OOP_sem_3.functions;

import Labs_OOP_sem_3.annotations.SimpleFunctionAnnotation;

@SimpleFunctionAnnotation(name = "Нулевая функция",priority = 2)
public class ZeroFunction extends ConstantFunction {

    public ZeroFunction() {
        super(0);
    }

}
