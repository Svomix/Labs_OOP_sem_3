package operations;


import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.Point;
import functions.TabulatedFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TabulatedFunctionOperationServiceTest {
    @Test
    void test() {
        TabulatedFunction function = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        Point[] points = new Point[]{new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(4, 4), new Point(5, 5)};
        Assertions.assertArrayEquals(points, TabulatedFunctionOperationService.asPoint(function));
    }

    @Test
    void test2() {
        TabulatedFunction function = new LinkedListTabulatedFunction(new double[]{0, 1.2, 2.5, 6.7, 8.8}, new double[]{1, 2, 3, 4, 5});
        Point[] points = new Point[]{new Point(0, 1), new Point(1.2, 2), new Point(2.5, 3), new Point(6.7, 4), new Point(8.8, 5)};
        Assertions.assertArrayEquals(points, TabulatedFunctionOperationService.asPoint(function));
    }

}