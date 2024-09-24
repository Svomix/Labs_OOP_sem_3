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
        double[] d = new double[][spline.getP()];
        for (int i = 0; i < d.length; ++i)
        {
            d[i] = spline.getC()[i + spline.getK() - spline.getP()];
        }
        for (int i = 1; i < d.length; ++i)
        {
            for(int j = d.length; j > i;--j)
            {
                double alpha = (x - spline.getT()[j + spline.getK() - spline.getP()]) / (spline.getT()[j + 1 + spline.getK() - i] - spline.getT()[j + spline.getK() - spline.getP()]);
                d[j] = (1 - alpha) * d[j - 1] + alpha * d[j];
            }
        }
        return d[spline.getP()];
    }
}
