package functions;

public class RungeKuttaFunction implements MathFunction {
    private double h; // Шаг сетки
    private double x0, y0;// Начальные значения
    private DifferentialEquations equations;

    public RungeKuttaFunction(double h, double x0, double y0, DifferentialEquations equations) {
        this.h = h;
        this.x0 = x0;
        this.y0 = y0;
        this.equations = equations;
    }

    private double k1(double x, double y) {
        return equations.getValues(x, y);
    }

    private double k2(double x, double y) {
        return equations.getValues(x + h / 2, y + h / 2 * k1(x, y));
    }

    private double k3(double x, double y) {
        return equations.getValues(x + h / 2, y + h / 2 * k2(x, y));
    }

    private double k4(double x, double y) {
        return equations.getValues(x + h, y + h * k3(x, y));
    }

    @Override
    public double apply(double x) { // x - конец интервала
        double y = 0;
        double yn = y0;
        double xn = x0;
        if (x >= xn)
            for (; xn < x; xn += h) {
                y = yn + h / 6 * (k1(xn, yn) + 2 * k2(xn, yn) + 2 * k3(xn, yn) + k4(xn, yn));
                yn = y;
            }
        else
            for (; xn > x; xn -= h) {
                y = yn + h / 6 * (k1(xn, yn) + 2 * k2(xn, yn) + 2 * k3(xn, yn) + k4(xn, yn));
                yn = y;
            }
        if (xn != x) {
            y = yn + h / 6 * (k1(xn, yn) + 2 * k2(xn, yn) + 2 * k3(xn, yn) + k4(xn, yn));
        }
        return y;
    }
}
