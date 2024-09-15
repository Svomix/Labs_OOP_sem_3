package functions;

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
}
