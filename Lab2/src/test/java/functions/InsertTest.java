package functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InsertTest
{
    private ArrayTabulatedFunction arr;
    private double[] arrX = new double[]{1,2,3,4,5,6};
     private double[] arrY= new double[]{1,2,3,4,5,6};
    @BeforeEach
    void createarr()
    {
        arr = new ArrayTabulatedFunction(arrX,arrY);
    }
    @Test
    void insertCenterTest()
    {
        arr.insert(4.5,10);
        Assertions.assertEquals(4.5,arr.getX(4));
        Assertions.assertEquals(10,arr.getY(4));
        arr.insert(5.5,12);
        Assertions.assertEquals(5.5,arr.getX(6));
        Assertions.assertEquals(12,arr.getY(6));
        arr.insert(1.5,3);
        Assertions.assertEquals(1.5,arr.getX(1));
        Assertions.assertEquals(3,arr.getY(1));
    }
    @Test
    void insertStartTest()
    {
        arr.insert(-2,3);
        Assertions.assertEquals(-2,arr.getX(0));
        Assertions.assertEquals(3,arr.getY(0));
    }
    @Test
    void insertFinishFinish()
    {
        arr.insert(10,15);
        Assertions.assertEquals(10,arr.getX(arr.count - 1));
        Assertions.assertEquals(15,arr.getY(arr.count - 1));
    }
    @Test
    void insertrelocatingFinish()
    {
        int size = arrX.length;
        arr.insert(0,2);
        Assertions.assertEquals(size * 2,arr.arrX.length);
        for (int i = 7; i < 14; ++i)
        {
            arr.insert(i, i * 2);
        }
        Assertions.assertEquals(size * 2 * 2,arr.arrX.length);
    }
}
