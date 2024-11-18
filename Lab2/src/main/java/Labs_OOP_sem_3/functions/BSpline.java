package Labs_OOP_sem_3.functions;

public class BSpline {
    private double[] t;
    private double[] arrControl;
    private int p;

    BSpline(double[] t, double[] arrControl, int p) {
        this.t = t;
        this.arrControl = arrControl;
        this.p = p;
    }

    public int getP() {
        return p;
    }

    public double[] getArrControl() {
        return arrControl;
    }

    public double[] getT() {
        return t;
    }

    public void setP(int p) {
        this.p = p;
    }

    public void setArrControl(double[] arrControl) {
        this.arrControl = arrControl;
    }

    public void setT(double[] t) {
        this.t = t;
    }

    public int whichInterval(double x) {
        for (int i = 1; i < t.length - 1; ++i) {
            if (x < t[i])
                return (i - 1);
            else if (x == t[t.length - 1])
                return (t.length - 1);
        }
        return -1;
    }
}
