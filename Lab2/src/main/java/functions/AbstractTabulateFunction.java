package functions;

public abstract class AbstractTabulateFunction implements MathFunction {
    protected int count;

    public int getCount() {
        return count;
    }

    protected double[] arrX;
    protected double[] arrY;

    public double getY(int index) {
        return arrY[index];
    }

    public double getX(int index) {
        return arrX[index];
    }

    abstract protected int indexOfX(double x);

    abstract protected int floorIndexOfX(double x);

    abstract protected double extrapolateLeft(double x);

    abstract protected double extrapolateRight(double x);

    abstract protected double interpolate(double x, int floorIndex);

    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return leftY + (rightY - leftY) / (rightX - leftX) * (x - leftX);
    }

    public double apply(double x) {
        if (arrX[0] > x)
            return extrapolateLeft(x);
        else if (arrX[count - 1] < x)
            return extrapolateRight(x);
        else if (indexOfX(x) != -1)
            return arrY[indexOfX(x)];
        int index = floorIndexOfX(x);
        return interpolate(x, arrX[index], arrX[index + 1], arrY[index], arrY[index + 1]);
    }
}
