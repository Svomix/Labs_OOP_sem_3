package concurrent;

import functions.TabulatedFunction;

public class Integrate {
    private final TabulatedFunction func;
    private volatile double integral;

    public Integrate(TabulatedFunction func) {
        this.func = func;
        integral = 0;
    }

    double SimpsonMethod(){
        double h = (func.rightBound() - func.leftBound()) / func.getCount();
        Runnable task1 = () -> {
            for (int i = 1; i < func.getCount() - 1; i += 2) {
                synchronized (func) {
                    integral += func.getY(i - 1);
                }
            }
        };
        Runnable task2 = () -> {
            for (int i = 1; i < func.getCount() - 1; i += 2) {
                synchronized (func) {
                    integral += func.getY(i);
                    integral *= 4;
                }
            }
        };

        Runnable task3 = () -> {
            for (int i = 1; i < func.getCount() - 1; i += 2) {
                synchronized (func) {
                    integral += func.getY(i + 1);
                }
            }
        };

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);
        Thread t3 = new Thread(task3);

        t1.start();
        t2.start();
        t3.start();
        synchronized (func) {
            integral *= h / 3;
        }

        while(t1.isAlive() || t2.isAlive() || t3.isAlive());

        return integral;
    }
}
