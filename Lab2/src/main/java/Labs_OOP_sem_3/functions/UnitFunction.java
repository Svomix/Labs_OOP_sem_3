package Labs_OOP_sem_3.functions;

import Labs_OOP_sem_3.annotations.SimpleFunctionAnnotation;

@SimpleFunctionAnnotation(name = "Единичная функция",priority = 2)
public class UnitFunction extends ConstantFunction {
    public UnitFunction() {
        super(1);
    }
}
