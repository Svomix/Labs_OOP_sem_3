package functions;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

class UnmodifiableTabulatedFunctionTest {
    @Test
    void test1() {
        TabulatedFunction func = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        UnmodifiableTabulatedFunction wrapper = new UnmodifiableTabulatedFunction(func);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> wrapper.setY(1, 2));
        Assertions.assertEquals(4, wrapper.indexOfX(5));
        Assertions.assertEquals(-1, wrapper.indexOfX(6));
        Assertions.assertEquals(-1, wrapper.indexOfY(6));
        Assertions.assertEquals(1, wrapper.indexOfY(2));
        Assertions.assertEquals(1, wrapper.leftBound());
        Assertions.assertEquals(5, wrapper.rightBound());
        Assertions.assertEquals(5, wrapper.getCount());
        Assertions.assertEquals(3, wrapper.getX(2));
        Assertions.assertEquals(3, wrapper.getY(2));
        Assertions.assertEquals(func.getY(0) + (func.getY(1) - func.getY(0)) / (func.getX(1) - func.getX(0)) * (-1 - func.getX(0)), wrapper.apply(-1));
    }

    @Test
    void test2() {
        TabulatedFunction func = new LinkedListTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        UnmodifiableTabulatedFunction wrapper = new UnmodifiableTabulatedFunction(func);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> wrapper.setY(1, 2));
        Assertions.assertEquals(4, wrapper.indexOfX(5));
        Assertions.assertEquals(-1, wrapper.indexOfX(6));
        Assertions.assertEquals(-1, wrapper.indexOfY(6));
        Assertions.assertEquals(1, wrapper.indexOfY(2));
        Assertions.assertEquals(1, wrapper.leftBound());
        Assertions.assertEquals(5, wrapper.rightBound());
        Assertions.assertEquals(5, wrapper.getCount());
        Assertions.assertEquals(3, wrapper.getX(2));
        Assertions.assertEquals(3, wrapper.getY(2));
        Assertions.assertEquals(func.getY(0) + (func.getY(1) - func.getY(0)) / (func.getX(1) - func.getX(0)) * (-1 - func.getX(0)), wrapper.apply(-1));

    }

    @Test
    void test3() {

    }

    @Test
    void iteratorTest() {
        ArrayTabulatedFunction arr = new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{1, 2, 3});
        UnmodifiableTabulatedFunction wrapper = new UnmodifiableTabulatedFunction(arr);
        Iterator<Point> iterator = wrapper.iterator();
        int j = 0;
        while (iterator.hasNext()) {
            Point point = iterator.next();
            Assertions.assertEquals(point.x, wrapper.getX(j));
            Assertions.assertEquals(point.y, wrapper.getY(j++));
        }
    }

}