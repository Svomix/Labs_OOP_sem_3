package Labs_OOP_sem_3.functions;

import java.util.Iterator;

public class UnmodifiableTabulatedFunction implements TabulatedFunction {
    TabulatedFunction func;

    public UnmodifiableTabulatedFunction(TabulatedFunction func) {
        this.func = func;
    }

    @Override
    public int getCount() {
        return func.getCount();
    }

    @Override
    public double getX(int index) {
        return func.getX(index);
    }

    @Override
    public double getY(int index) {
        return func.getY(index);
    }

    @Override
    public void setY(int index, double value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOfX(double x) {
        return func.indexOfX(x);
    }

    @Override
    public int indexOfY(double y) {
        return func.indexOfY(y);
    }

    @Override
    public double leftBound() {
        return func.leftBound();
    }

    @Override
    public double rightBound() {
        return func.rightBound();
    }

    @Override
    public double apply(double x) {
        return func.apply(x);
    }

    @Override
    public Iterator<Point> iterator() {
        return func.iterator();
    }
}
