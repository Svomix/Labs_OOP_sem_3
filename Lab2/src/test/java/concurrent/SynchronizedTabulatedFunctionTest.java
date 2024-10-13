package concurrent;


import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;
import exceptions.InterpolationException;
import functions.ArrayTabulatedFunction;
import functions.Point;
import functions.SqrFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

class SynchronizedTabulatedFunctionTest {
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
        SynchronizedTabulatedFunction wrapper = new SynchronizedTabulatedFunction(func);
        Assertions.assertEquals(-1, wrapper.indexOfX(6));
    }

    @Test
    void indexOfYTest1() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        SynchronizedTabulatedFunction wrapper = new SynchronizedTabulatedFunction(func);
        Assertions.assertEquals(-1, wrapper.indexOfY(6));
    }

    @Test
    void indexOfYTest2() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        SynchronizedTabulatedFunction wrapper = new SynchronizedTabulatedFunction(func);
        Assertions.assertEquals(1, wrapper.indexOfY(2));
    }

    @Test
    void leftBoundTest() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        SynchronizedTabulatedFunction wrapper = new SynchronizedTabulatedFunction(func);
        Assertions.assertEquals(1, wrapper.leftBound());
    }

    @Test
    void rightBoundTest() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        SynchronizedTabulatedFunction wrapper = new SynchronizedTabulatedFunction(func);
        Assertions.assertEquals(5, wrapper.rightBound());
    }


    @Test
    void extrapolateLeftTest() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        SynchronizedTabulatedFunction wrapper = new SynchronizedTabulatedFunction(func);
        Assertions.assertEquals(arrY[0] + (arrY[1] - arrY[0]) / (arrX[1] - arrX[0]) * (-1 - arrX[0]),wrapper.apply(-1) );
    }

    @Test
    void extrapolateRightTest() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        SynchronizedTabulatedFunction wrapper = new SynchronizedTabulatedFunction(func);
        Assertions.assertEquals(arrY[func.getCount() - 2] + (arrY[func.getCount() - 1] - arrY[func.getCount() - 2]) / (arrX[func.getCount() - 1] - arrX[func.getCount() - 2]) * (6 - arrX[func.getCount() - 2]), wrapper.apply(6));
    }


    @Test
    void getXTest() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        SynchronizedTabulatedFunction wrapper = new SynchronizedTabulatedFunction(func);
        Assertions.assertEquals(4, wrapper.getX(3));
    }

    @Test
    void getYTest() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        SynchronizedTabulatedFunction wrapper = new SynchronizedTabulatedFunction(func);
        Assertions.assertEquals(3, wrapper.getX(2));
    }

    @Test
    void setYTest() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 2, 3, 4, 5};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        SynchronizedTabulatedFunction wrapper = new SynchronizedTabulatedFunction(func);
        wrapper.setY(2, 5);
        Assertions.assertEquals(5, wrapper.getY(2));
    }

    @Test
    void constructFunction() {
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(new SqrFunction(), 0, 5, 6);
        SynchronizedTabulatedFunction wrapper = new SynchronizedTabulatedFunction(func);
        boolean b = false;
        for (int i = 0; i < wrapper.getCount(); i++) {
            if (wrapper.getY(i) != i * i && wrapper.getX(i) != i)
                b = true;
        }
        Assertions.assertFalse(b);
    }

    @Test
    void constructFunction2() {
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(new SqrFunction(), 5, 0, 6);
        SynchronizedTabulatedFunction wrapper = new SynchronizedTabulatedFunction(func);
        boolean b = false;
        for (int i = 0; i < wrapper.getCount(); i++) {
            if (wrapper.getY(i) != i * i && wrapper.getX(i) != i)
                b = true;
        }
        Assertions.assertFalse(b);
    }

    @Test
    void iteratorTest1(){
        ArrayTabulatedFunction arr = new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{1, 2, 3});
        SynchronizedTabulatedFunction wrapper = new SynchronizedTabulatedFunction(arr);
        Iterator<Point> iterator = wrapper.iterator();
        int j = 0;
        while(iterator.hasNext()){
            Point point = iterator.next();
            Assertions.assertEquals(point.x, wrapper.getX(j));
            Assertions.assertEquals(point.y, wrapper.getY(j++));
        }
    }

    @Test
    void iteratorTest2(){
        ArrayTabulatedFunction arr = new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{1, 2, 3});
        SynchronizedTabulatedFunction wrapper = new SynchronizedTabulatedFunction(arr);
        int j = 0;
        for (Point point : wrapper) {
            Assertions.assertEquals(point.x, wrapper.getX(j));
            Assertions.assertEquals(point.y, wrapper.getY(j++));
        }
    }

    @Test
    void sychronized(){
        ArrayTabulatedFunction arr = new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{1, 2, 3});
        SynchronizedTabulatedFunction wrapper = new SynchronizedTabulatedFunction(arr);
        Assertions.assertEquals(3.0, (double) wrapper.doSynchronously(SynchronizedTabulatedFunction::getCount));
        wrapper.doSynchronously(func -> {
            func.setY(1, 3);
            return null;
        });
        Assertions.assertEquals(1, wrapper.getX(0));
        Assertions.assertEquals(3, wrapper.getY(1));
        Assertions.assertEquals(1, wrapper.doSynchronously(SynchronizedTabulatedFunction::leftBound));
        wrapper.doSynchronously(func -> {
            func.setY(2, 0);
            return null;
        });
        Assertions.assertEquals(3, wrapper.doSynchronously(SynchronizedTabulatedFunction::rightBound));
        Assertions.assertEquals(0, wrapper.getY(wrapper.getCount() - 1));
    }


}