package concurrent;

import functions.Point;
import functions.TabulatedFunction;
import operations.TabulatedFunctionOperationService;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SynchronizedTabulatedFunction implements TabulatedFunction {
    final TabulatedFunction func;

    public SynchronizedTabulatedFunction(TabulatedFunction func) {
        this.func = func;
    }

    @Override
    public int getCount() {
        synchronized (func) {
            return func.getCount();
        }
    }

    @Override
    public double getX(int index) {
        synchronized (func) {
            return func.getX(index);
        }
    }

    @Override
    public double getY(int index) {
        synchronized (func) {
            return func.getY(index);
        }
    }

    @Override
    public void setY(int index, double value) {
        synchronized (func) {
            func.setY(index, value);
        }
    }

    @Override
    public int indexOfX(double x) {
        synchronized (func) {
            return func.indexOfX(x);
        }
    }

    @Override
    public int indexOfY(double y) {
        synchronized (func) {
            return func.indexOfY(y);
        }
    }

    @Override
    public double leftBound() {
        synchronized (func) {
            return func.leftBound();
        }
    }

    @Override
    public double rightBound() {
        synchronized (func) {
            return func.rightBound();
        }
    }

    @Override
    public double apply(double x) {
        synchronized (func) {
            return func.apply(x);
        }
    }

    @Override
    public Iterator<Point> iterator() {
        synchronized (func) {
           Point[] points =  TabulatedFunctionOperationService.asPoint(func);
           return new Iterator<Point>() {
               private int i = 0;
               @Override
               public boolean hasNext()
               {
                   return i < func.getCount();
               }
               @Override
               public Point next() {
                   if (!hasNext())
                       throw new NoSuchElementException();
                   Point p = new Point(func.getX(i),func.getY(i));
                   ++i;
                   return p;
               }
           };
        }
    }

    public interface Operation<T> {
        T apply(SynchronizedTabulatedFunction func);
    }

    public <T> T doSynchronously(Operation<? extends T> op) {
        synchronized (func) {
            return op.apply(this);
        }
    }


}
