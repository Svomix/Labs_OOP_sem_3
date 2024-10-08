package operations;

import exceptions.InconsistentFunctionsException;
import functions.Point;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

public class TabulatedFunctionOperationService {

    TabulatedFunctionFactory factory;

    TabulatedFunctionOperationService() {
        factory = new ArrayTabulatedFunctionFactory();
    }

    TabulatedFunctionOperationService(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    public TabulatedFunctionFactory getFactory() {
        return factory;
    }

    public void setFactory(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    public static Point[] asPoint(TabulatedFunction tabulatedFunction) {
        Point[] points = new Point[tabulatedFunction.getCount()];
        int i = 0;
        for (Point point : tabulatedFunction) {
            points[i++] = new Point(point.x, point.y);
        }
        return points;
    }

    private TabulatedFunction doOperation(TabulatedFunction a, TabulatedFunction b, BiOperation operation){
        if (a.getCount() != b.getCount()) throw new InconsistentFunctionsException("Different size of TabulatedFunctions");
        Point[] pointsA = asPoint(a);
        Point[] pointsB = asPoint(b);
        double[] xValues = new double[pointsA.length];
        double[] yValues = new double[pointsA.length];
        for (int i = 0; i < pointsA.length; i++) {
            if (pointsA[i].x != pointsB[i].x) throw new InconsistentFunctionsException("X coordinates do not match");
            xValues[i] = pointsA[i].x;
            yValues[i] = operation.apply(pointsA[i].y, pointsB[i].y);
        }

        return factory.create(xValues, yValues);
    }

    public TabulatedFunction addition(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, Double::sum);
    }

    public TabulatedFunction subtraction(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, (x, y) -> x - y);
    }

    private interface BiOperation {
        double apply(double x, double y);
    }
}
