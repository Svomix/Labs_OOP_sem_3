package Labs_OOP_sem_3.functions;

import Labs_OOP_sem_3.exceptions.ArrayIsNotSortedException;
import Labs_OOP_sem_3.exceptions.DifferentLengthOfArraysException;
import Labs_OOP_sem_3.exceptions.InterpolationException;
import Labs_OOP_sem_3.functions.ArrayTabulatedFunction;
import Labs_OOP_sem_3.functions.Point;
import Labs_OOP_sem_3.functions.SqrFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Objects;


class ArrayTabulatedFunctionTest {

    @Test
    void indexOfXTest1() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        Assertions.assertEquals(4, func.indexOfX(5));
    }

    @Test
    void indexOfXTest2() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        Assertions.assertEquals(-1, func.indexOfX(6));
    }

    @Test
    void indexOfYTest1() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        Assertions.assertEquals(-1, func.indexOfY(6));
    }

    @Test
    void indexOfYTest2() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        Assertions.assertEquals(1, func.indexOfY(2));
    }

    @Test
    void leftBoundTest() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        Assertions.assertEquals(1, func.leftBound());
    }

    @Test
    void rightBoundTest() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        Assertions.assertEquals(5, func.rightBound());
    }

    @Test
    void floorIndexOfXTest1() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        Assertions.assertEquals(4, func.floorIndexOfX(5));
    }

    @Test
    void floorIndexOfXTest2() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        Assertions.assertEquals(3, func.floorIndexOfX(4.5));
    }

    @Test
    void floorIndexOfXTest4() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        Assertions.assertEquals(func.getCount(), func.floorIndexOfX(6));
    }

    @Test
    void extrapolateLeftTest() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        Assertions.assertEquals(arrY[0] + (arrY[1] - arrY[0]) / (arrX[1] - arrX[0]) * (-1 - arrX[0]), func.apply(-1));
    }

    @Test
    void extrapolateRightTest() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        Assertions.assertEquals(arrY[func.getCount() - 2] + (arrY[func.getCount() - 1] - arrY[func.getCount() - 2]) / (arrX[func.getCount() - 1] - arrX[func.getCount() - 2]) * (6 - arrX[func.getCount() - 2]), func.apply(6));
    }

    @Test
    void interpolateTest() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        int floorIndex = func.floorIndexOfX(4.5);
        Assertions.assertEquals(arrY[floorIndex] + (arrY[floorIndex + 1] - arrY[floorIndex]) / (arrX[floorIndex + 1] - arrX[floorIndex]) * (4.5 - arrX[floorIndex]), func.apply(4.5));
    }

    @Test
    void getXTest() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        Assertions.assertEquals(4, func.getX(3));
    }

    @Test
    void getYTest() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        Assertions.assertEquals(3, func.getX(2));
    }

    @Test
    void setYTest() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        func.setY(2, 5);
        Assertions.assertEquals(5, func.getY(2));
    }

    @Test
    void constructFunction() {
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(new SqrFunction(), 0, 5, 6);
        boolean b = false;
        for (int i = 0; i < func.getCount(); i++) {
            if (func.getY(i) != i * i && func.getX(i) != i)
                b = true;
        }
        Assertions.assertFalse(b);
    }

    @Test
    void constructFunction2() {
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(new SqrFunction(), 5, 0, 6);
        boolean b = false;
        for (int i = 0; i < func.getCount(); i++) {
            if (func.getY(i) != i * i && func.getX(i) != i)
                b = true;
        }
        Assertions.assertFalse(b);
    }

    @Test
    void removeTest1() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        func.remove(2);
        Assertions.assertEquals(4, func.getX(2));
    }

    @Test
    void removeTest2() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        func.remove(0);
        Assertions.assertEquals(2, func.getX(0));
    }

    @Test
    void removeTest3() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        func.remove(func.getCount() - 1);
        Assertions.assertEquals(4, func.getX(func.getCount() - 1));
    }

    @Test
    void createObject() {
        Assertions.assertThrows(ArrayIsNotSortedException.class, () -> new ArrayTabulatedFunction(new double[]{1, 3, 2}, new double[]{1, 2, 3}));
        Assertions.assertThrows(DifferentLengthOfArraysException.class, () -> new ArrayTabulatedFunction(new double[]{1, 3, 2}, new double[]{1, 2}));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ArrayTabulatedFunction(new double[]{1}, new double[]{1}));
        Assertions.assertDoesNotThrow(() -> new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{1, 2, 3}));
    }

    @Test
    void interpolateFloorTest() {
        ArrayTabulatedFunction arr = new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{1, 2, 3});
        Assertions.assertThrows(InterpolationException.class, () -> arr.interpolate(1.5, 1));
        Assertions.assertDoesNotThrow(() -> arr.interpolate(1.5, 0));
    }

    @Test
    void iteratorTest1() {
        ArrayTabulatedFunction arr = new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{1, 2, 3});
        Iterator<Point> iterator = arr.iterator();
        int j = 0;
        while (iterator.hasNext()) {
            Point point = iterator.next();
            Assertions.assertEquals(Objects.hash(point.x, point.y), point.hashCode());
            Assertions.assertEquals(point.x, arr.getX(j));
            Assertions.assertEquals(point.y, arr.getY(j++));
        }
    }

    @Test
    void iteratorTest2() {
        ArrayTabulatedFunction arr = new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{1, 2, 3});
        int j = 0;
        for (Point point : arr) {
            Assertions.assertEquals(point.x, arr.getX(j));
            Assertions.assertEquals(point.y, arr.getY(j++));
        }
    }

    @Test
    void stringTest() {
        ArrayTabulatedFunction arr = new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{1, 2, 3});
        Assertions.assertEquals("ArrayTabulatedFunction size = 3\n[1.0; 1.0]\n[2.0; 2.0]\n[3.0; 3.0]\n", arr.toString());
    }
}