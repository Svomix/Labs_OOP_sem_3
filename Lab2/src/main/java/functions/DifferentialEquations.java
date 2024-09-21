package functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class DifferentialEquations {
    //private List<Double> list;
    private BiFunction<Double, Double, Double> function;
    /*public DifferentialEquations(double[] arr) {
        list = Arrays.asList(Arrays.stream(arr).boxed().toArray(Double[]::new));
    }

     */
    public DifferentialEquations(BiFunction<Double, Double, Double> function) {
        this.function = function;
    }

    public double getValues(double x, double y){
        return function.apply(x, y);
    }
    public double getValues(double x){
        return function.apply(x, 1.0);
    }

    // y' = f(x, y)
    //f(x, y) = sin(y) + x ^ 3
    //f(x, y) = y ^ x
    //f(x, y) = sin(x^y)
}
