package functions;

public class BSpline
{
    private int k;
    private int[] t;
    private int[] c;
    private int p;

    public int getP() {
        return p;
    }

    public int[] getC() {
        return c;
    }

    public int[] getT() {
        return t;
    }

    public int getK() {
        return k;
    }

    public void setP(int p) {
        this.p = p;
    }

    public void setC(int[] c) {
        this.c = c;
    }

    public void setT(int[] t) {
        this.t = t;
    }

    public void setK(int k) {
        this.k = k;
    }
}
