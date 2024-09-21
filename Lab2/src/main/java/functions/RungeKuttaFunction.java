package functions;

public class RungeKuttaFunction implements MathFunction{
    private double h; // Шаг сетки
    private double x0, y0;// Начальные значения
    private DifferentialEquations equations;
    public RungeKuttaFunction(double h, double x0, double y0, DifferentialEquations equations) {
        this.h = h;
        this.x0 = x0;
        this.y0 = y0;
        this.equations = equations;
    }
    private double k1(double x, double y){
        return equations.getValues(x, y);
    }
    private double k2(double x, double y){
        return equations.getValues(x + h / 2, y + h / 2 * k1(x, y));
    }
    private double k3(double x, double y){
        return equations.getValues(x + h / 2, y + h / 2 * k2(x, y));
    }
    private double k4(double x, double y){
        return equations.getValues(x + h, y + h * k3(x, y));
    }

    @Override
    public double apply(double x) {
        DifferentialEquations de = new DifferentialEquations((x1, y1) -> Math.sin(Math.pow(x1, y1)));
        double y = equations.getValues(x);
        return y + h / 6 * (k1(x, y) + 2 * k2(x, y) + 2 * k3(x, y) + k4(x, y));
    }
}
