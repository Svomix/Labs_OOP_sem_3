package concurrent;

import functions.TabulatedFunction;

public class MultiplyingTask implements Runnable {
    private final TabulatedFunction func;
    private static final Object lock = new Object();

    public MultiplyingTask(TabulatedFunction func) {
        this.func = func;
    }

    @Override
    public void run() {
        for (int i = 0; i < func.getCount(); i++)
            synchronized (lock) {
                func.setY(i, func.getY(i) * 2);
            }

        System.out.println("Поток " + Thread.currentThread().getName() + " закончил выполнение задачи");
    }

    public TabulatedFunction getFunc() {
        return func;
    }
}
