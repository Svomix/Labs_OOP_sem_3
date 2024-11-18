package Labs_OOP_sem_3.concurrent;

import Labs_OOP_sem_3.functions.TabulatedFunction;

public class Integrate {
    private final TabulatedFunction func;
    private volatile double integral;

    public Integrate(TabulatedFunction func) {
        this.func = func;
        integral = 0;
    }

    double SimpsonMethod() throws InterruptedException {
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
                    integral += func.getY(i) * 4;
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

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (func) {
            integral = integral * h / 3;
        }
        return integral;

    }
}
