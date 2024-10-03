package functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LinkedListTabulatedFunctionTest {
    private LinkedListTabulatedFunction list;
    private double[] a = {0, 1, 2, 3, 4, 5, 6, 7};
    private double[] b = {0, 1, 4, 9, 16, 25, 36, 49};

    @Test
    void createList4argumentsTestInts() {
        MathFunction func = new SqrFunction();
        list = new LinkedListTabulatedFunction(func, 0, 10, 11);
        for (int i = 0; i < 11; i++) {
            System.out.println(list.getY(i));
        }
    }

    @Test
    void createList4argumentsTestRotating() {
        MathFunction func = new SqrFunction();
        list = new LinkedListTabulatedFunction(func, 10, 0, 11);
        for (int i = 0; i < 11; i++) {
            System.out.println(list.getY(i));
        }
    }

    @Test
    void createList4argumentsTestSimilar() {
        MathFunction func = new SqrFunction();
        list = new LinkedListTabulatedFunction(func, 10, 10, 10);
        for (int i = 0; i < 11; i++) {
            System.out.println(list.getY(i));
        }
    }

    @Test
    void createList4argumentsTestDoubles() {
        MathFunction func = new SqrFunction();
        list = new LinkedListTabulatedFunction(func, 5.5, 13.2, 4);
        for (int i = 0; i < 4; i++) {
            System.out.println(list.getY(i));
        }
    }

    @BeforeEach
    void createList2argumentsTest() {
        list = new LinkedListTabulatedFunction(a, b);
    }

    @Test
    void getXTest() {
        double res = list.getX(4);
        Assertions.assertEquals(4, res);
    }

    @Test
    void getYTest() {
        double res = list.getY(3);
        Assertions.assertEquals(9, res);
    }

    @Test
    void setYTest() {
        int index = 2;
        double y = 4;
        list.setY(2, 4);
        Assertions.assertEquals(4, list.getY(2));
    }

    @Test
    void leftBoundTest() {
        double res = list.leftBound();
        Assertions.assertEquals(0, res);
    }

    @Test
    void rightBoundTest() {
        double res = list.rightBound();
        Assertions.assertEquals(7, res);
    }

    @Test
    void indexOfXExistingTest() {
        int res = list.indexOfX(4);
        Assertions.assertEquals(4, res);
    }

    @Test
    void indexOfXNotExistingTest() {
        int res = list.indexOfX(10);
        Assertions.assertEquals(-1, res);
    }

    @Test
    void indexOfYExistingTest() {
        int res = list.indexOfY(4);
        Assertions.assertEquals(2, res);
    }

    @Test
    void indexOfYNotExistingTest() {
        int res = list.indexOfY(100);
        Assertions.assertEquals(-1, res);
    }

    @Test
    void floorIndexOfXMiddleTest() {
        int res = list.floorIndexOfX(5.5);
        Assertions.assertEquals(5, res);
    }

    @Test
    void floorIndexOfXBoundTest() {
        int res = list.floorIndexOfX(100);
        Assertions.assertEquals(8, res);
    }

    @Test
    void getCountTest() {
        Assertions.assertEquals(8, list.getCount());
    }

    @Test
    void removeMiddleTest() {
        list.remove(2);
        list.remove(5);
        Assertions.assertEquals(3, list.getX(2));
        Assertions.assertEquals(7, list.getX(5));
    }

    @Test
    void removeLeftBoundTest() {
        list.remove(0);
        Assertions.assertEquals(1, list.getX(0));
    }

    @Test
    void removeRightBoundTest() {
        list.remove(list.getCount() - 1);
        Assertions.assertEquals(6, list.getX(list.getCount() - 1));
    }
    @Test
    void extrapolateRightTest() {
        double a = list.apply(9);
        Assertions.assertEquals(75,a);
    }
    @Test
    void extrapolateLeftTest() {
        double a = list.apply(-2);
        Assertions.assertEquals(-2,a);
    }
    @Test
    void interpolateTest1() {
        double a = list.apply(2.5);
        Assertions.assertEquals(6.5,a);
    }
    @Test
    void interpolateTest2() {
        double a = list.interpolate(4.2,4);
        Assertions.assertEquals(17.8,a);
    }
    @Test
    void applyTest1()
    {
        double a = list.apply(4.2);
        Assertions.assertEquals(17.8,a);
    }
    @Test
    void applyTest2()
    {
        double a = list.apply(6);
        Assertions.assertEquals(36,a);
    }
}
