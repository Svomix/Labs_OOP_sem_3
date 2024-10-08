package operations;

import functions.Point;
import functions.TabulatedFunction;

public class TabulatedFunctionOperationService {
    public static Point[] asPoint(TabulatedFunction tabulatedFunction){
        Point[] points = new Point[tabulatedFunction.getCount()];
        int i = 0;
        for (Point point : tabulatedFunction) {
            points[i++] = new Point(point.x, point.y);
        }
        return points;
    }
}
