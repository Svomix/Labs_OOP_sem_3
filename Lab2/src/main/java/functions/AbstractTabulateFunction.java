package functions;

import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;

public abstract class AbstractTabulateFunction implements MathFunction {
    protected int count;

    public int getCount() {
        return count;
    }

    abstract public double getY(int index);

    abstract public double getX(int index);

    abstract protected int indexOfX(double x);

    abstract protected int floorIndexOfX(double x);

    abstract protected double extrapolateLeft(double x);

    abstract protected double extrapolateRight(double x);

    abstract protected double interpolate(double x, int floorIndex);

    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return leftY + (rightY - leftY) / (rightX - leftX) * (x - leftX);
    }

    public double apply(double x) {
        if (getX(0) > x)
            return extrapolateLeft(x);
        else if (getX(count - 1) < x)
            return extrapolateRight(x);
        else if (indexOfX(x) != -1)
            return getY(indexOfX(x));
        int index = floorIndexOfX(x);
        return interpolate(x, getX(index), getX(index + 1), getY(index), getY(index + 1));
    }

    static void checkLengthIsTheSame(double[] xValues, double[] yValues) {
        if (xValues.length != yValues.length)  throw new DifferentLengthOfArraysException("Different length arrays");
    }

    static void checkSorted(double[] xValues){
        for (int i = 1; i < xValues.length; i++)
            if (xValues[i] <= xValues[i-1]) throw new ArrayIsNotSortedException("Array is not sorted");
    }
}
