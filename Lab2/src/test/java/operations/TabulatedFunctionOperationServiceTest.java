package operations;


import exceptions.InconsistentFunctionsException;
import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.Point;
import functions.TabulatedFunction;
import functions.factory.TabulatedFunctionFactory;
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

    @Test
    void test3() {
        TabulatedFunction function1 = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunction function2 = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        TabulatedFunction tabulated = service.addition(function1, function2);
        Point[] points = {new Point(1, 2), new Point(2, 4), new Point(3, 6), new Point(4, 8), new Point(5, 10)};
        Assertions.assertArrayEquals(points, TabulatedFunctionOperationService.asPoint(tabulated));
    }

    @Test
    void test4() {
        TabulatedFunction function1 = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunction function2 = new LinkedListTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        TabulatedFunction tabulated = service.addition(function1, function2);
        Point[] points = {new Point(1, 2), new Point(2, 4), new Point(3, 6), new Point(4, 8), new Point(5, 10)};
        Assertions.assertArrayEquals(points, TabulatedFunctionOperationService.asPoint(tabulated));
    }

    @Test
    void test5() {
        TabulatedFunction function1 = new LinkedListTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunction function2 = new LinkedListTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        TabulatedFunction tabulated = service.addition(function1, function2);
        Point[] points = {new Point(1, 2), new Point(2, 4), new Point(3, 6), new Point(4, 8), new Point(5, 10)};
        Assertions.assertArrayEquals(points, TabulatedFunctionOperationService.asPoint(tabulated));
    }

    @Test
    void test6() {
        TabulatedFunction function1 = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunction function2 = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4}, new double[]{1, 2, 3, 4});
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        Assertions.assertThrows(InconsistentFunctionsException.class, () -> service.addition(function1, function2));
    }

    @Test
    void test7() {
        TabulatedFunction function1 = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunction function2 = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        TabulatedFunction tabulated = service.subtraction(function1, function2);
        Point[] points = {new Point(1, 0), new Point(2, 0), new Point(3, 0), new Point(4, 0), new Point(5, 0)};
        Assertions.assertArrayEquals(points, TabulatedFunctionOperationService.asPoint(tabulated));
    }

    @Test
    void test8() {
        TabulatedFunction function1 = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunction function2 = new LinkedListTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        TabulatedFunction tabulated = service.subtraction(function1, function2);
        Point[] points = {new Point(1, 0), new Point(2, 0), new Point(3, 0), new Point(4, 0), new Point(5, 0)};
        Assertions.assertArrayEquals(points, TabulatedFunctionOperationService.asPoint(tabulated));
    }

    @Test
    void test9() {
        TabulatedFunction function1 = new LinkedListTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunction function2 = new LinkedListTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        TabulatedFunction tabulated = service.subtraction(function1, function2);
        Point[] points = {new Point(1, 0), new Point(2, 0), new Point(3, 0), new Point(4, 0), new Point(5, 0)};
        Assertions.assertArrayEquals(points, TabulatedFunctionOperationService.asPoint(tabulated));
    }

    @Test
    void test10() {
        TabulatedFunction function1 = new LinkedListTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunction function2 = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        TabulatedFunction tabulated = service.subtraction(function1, function2);
        Point[] points = {new Point(1, 0), new Point(2, 0), new Point(3, 0), new Point(4, 0), new Point(5, 0)};
        Assertions.assertArrayEquals(points, TabulatedFunctionOperationService.asPoint(tabulated));
    }

    @Test
    void test11() {
        TabulatedFunction function1 = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunction function2 = new ArrayTabulatedFunction(new double[]{0, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        Assertions.assertThrows(InconsistentFunctionsException.class, () -> service.subtraction(function1, function2));
    }

}