package Labs_OOP_sem_3.concurrent;

import Labs_OOP_sem_3.functions.TabulatedFunction;

public class ReadTask implements Runnable {
    private TabulatedFunction func;

    ReadTask(TabulatedFunction func) {
        this.func = func;
    }

    @Override
    public void run() {
        for (int i = 0; i < func.getCount(); ++i) {
            synchronized (func) {
                System.out.printf("After read: i = %d, x = %f, y = %f\n", i, func.getX(i), func.getY(i));
            }
        }
    }

}
