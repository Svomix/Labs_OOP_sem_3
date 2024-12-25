package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.annotations.SimpleFunctionAnnotation;
import Labs_OOP_sem_3.dto.MathFunctionDto;
import Labs_OOP_sem_3.exceptions.NoSuchSimpleFunctionException;
import Labs_OOP_sem_3.functions.MathFunction;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReflectionService {
    static final String FUNCTIONS_PREFIX = "Labs_OOP_sem_3.functions";

    public List<MathFunctionDto> getAllSimpleFunctions() {
        Reflections reflections = new Reflections(FUNCTIONS_PREFIX);

        return reflections.getTypesAnnotatedWith(SimpleFunctionAnnotation.class).stream().sorted(
                (a, b) -> {
                    SimpleFunctionAnnotation annotation1 = a.getAnnotation(SimpleFunctionAnnotation.class);
                    SimpleFunctionAnnotation annotation2 = b.getAnnotation(SimpleFunctionAnnotation.class);
                    Comparator<String> stringComparator = Comparator.naturalOrder();
                    Comparator<Integer> integerComparator = Comparator.reverseOrder();
                    return integerComparator.compare(annotation1.priority(), annotation2.priority()) * 100000 + stringComparator.compare(annotation1.name(), annotation2.name());
                }
        ).map((func) -> {
            SimpleFunctionAnnotation annotation = func.getAnnotation(SimpleFunctionAnnotation.class);
            return new MathFunctionDto(func.getSimpleName(), annotation.name());
        }).collect(Collectors.toList());
    }

    public MathFunction create(String classname) throws NoSuchSimpleFunctionException {
        try {
            Class<?> rawClass = Class.forName(FUNCTIONS_PREFIX + "." + classname);

            Class<? extends MathFunction> funcClass = rawClass.asSubclass(MathFunction.class);

            return funcClass.getConstructor().newInstance();
        } catch (ClassNotFoundException | ClassCastException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException | InvocationTargetException exception) {
            throw new NoSuchSimpleFunctionException(exception.getMessage());
        }
    }
}
