package functions.mock;

import functions.AbstractTabulateFunction;

public class MockTabulatedFunction extends AbstractTabulateFunction {

    private double[] arrX;
    private double[] arrY;

    public MockTabulatedFunction() {
        count = 2;
        arrX = new double[count];
        arrX[0] = 1;
        arrX[1] = 2.5;
        arrY = new double[count];
        arrY[0] = -1;
        arrY[1] = -5;
    }

    @Override
    public double getY(int index) {
        return arrY[index];
    }

    @Override
    public double getX(int index) {
        return arrX[index];
    }

    @Override
    protected int indexOfX(double x) {
        if (arrX[0] == x) return 0;
        if (arrX[1] == x) return 1;
        return -1;
    }

    @Override
    protected int floorIndexOfX(double x) {
        if (x > arrX[1]) return count;
        if (indexOfX(x) != -1) return indexOfX(x);
        return 0;
    }

    @Override
    protected double extrapolateLeft(double x) {
        return arrY[0] + (arrY[1] - arrY[0]) / (arrX[1] - arrX[0]) * (x - arrX[0]);
    }

    @Override
    protected double extrapolateRight(double x) {
        return arrY[0] + (arrY[1] - arrY[0]) / (arrX[1] - arrX[0]) * (x - arrX[0]);
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        return arrY[0] + (arrY[1] - arrY[0]) / (arrX[1] - arrX[0]) * (x - arrX[0]);
    }

    public double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return super.interpolate(x, leftX, rightX, leftY, rightY);
    }
}
