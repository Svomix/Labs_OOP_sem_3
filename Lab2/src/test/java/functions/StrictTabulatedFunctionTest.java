package functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StrictTabulatedFunctionTest {
    @Test
    void strictLinkedListTest() {
        double[] a = {0, 1, 2, 3, 4, 5, 6, 7};
        double[] b = {0, 1, 4, 9, 16, 25, 36, 49};
        LinkedListTabulatedFunction list = new LinkedListTabulatedFunction(a, b);
        var strictlist = new StrictTabulatedFunction(list);
        Assertions.assertEquals(3, strictlist.getX(3));
        list.insert(8, 64);
        Assertions.assertEquals(64, strictlist.getY(8));
        Assertions.assertEquals(9, strictlist.getCount());
        Assertions.assertEquals(0, strictlist.leftBound());
        Assertions.assertEquals(8, strictlist.rightBound());
        Assertions.assertEquals(5, strictlist.indexOfX(5));
        Assertions.assertEquals(6, strictlist.indexOfY(36));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> strictlist.apply(2.6));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> strictlist.apply(10));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> strictlist.apply(-10));
        Assertions.assertEquals(16, strictlist.apply(4));
    }

    @Test
    void strictArrayTabulatedTest() {
        double[] a = {0, 1, 2, 3, 4, 5, 6, 7};
        double[] b = {0, 1, 4, 9, 16, 25, 36, 49};
        ArrayTabulatedFunction arr = new ArrayTabulatedFunction(a, b);
        var strictarr = new StrictTabulatedFunction(arr);
        double[] x = new double[a.length];
        int i = 0;
        for (Point p : strictarr) {
            x[i] = p.x;
            ++i;
        }
        Assertions.assertArrayEquals(a, x);
        Assertions.assertEquals(3, strictarr.getX(3));
        arr.insert(8, 64);
        Assertions.assertEquals(64, strictarr.getY(8));
        Assertions.assertEquals(9, strictarr.getCount());
        Assertions.assertEquals(0, strictarr.leftBound());
        Assertions.assertEquals(8, strictarr.rightBound());
        Assertions.assertEquals(5, strictarr.indexOfX(5));
        Assertions.assertEquals(6, strictarr.indexOfY(36));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> strictarr.apply(2.6));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> strictarr.apply(10));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> strictarr.apply(-10));
        Assertions.assertEquals(16, strictarr.apply(4));
    }
}
