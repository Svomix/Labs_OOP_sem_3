package functions;

public class DeBoorFunction implements MathFunction
{
    private BSpline spline;
    public DeBoorFunction(BSpline spline)
    {
        this.spline = spline;
    }
    @Override
    public double apply(double x)
    {
        final int indexSig = spline.whichInterval(x);
        double[] d = new double[spline.getP() + 1];
        for (int i = 0; i <= spline.getP(); ++i)
        {
            d[i] = spline.getArrControl()[i + indexSig - spline.getP()];
        }
        for (int i = 1; i <= spline.getP(); ++i)
        {
            for(int j = spline.getP(); j >= i;--j)
            {
                double alpha = (x - spline.getT()[j + indexSig - spline.getP()]) / (spline.getT()[j + 1 + indexSig - i] - spline.getT()[j + indexSig - spline.getP()]);
                d[j] = (1 - alpha) * d[j - 1] + alpha * d[j];
            }
        }
        return d[spline.getP()];
    }
}
