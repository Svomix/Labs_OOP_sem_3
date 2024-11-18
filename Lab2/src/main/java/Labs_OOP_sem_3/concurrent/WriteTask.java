package Labs_OOP_sem_3.concurrent;

import Labs_OOP_sem_3.functions.TabulatedFunction;

public class WriteTask implements Runnable {
    private TabulatedFunction func;
    private double value;

    WriteTask(TabulatedFunction func, double value) {
        this.func = func;
        this.value = value;
    }

    @Override
    public void run() {
        for (int i = 0; i < func.getCount(); i++) {
            synchronized (func) {
                func.setY(i, value);
                System.out.printf("Writing for index %d complete\n", i);
            }
        }
    }
}
