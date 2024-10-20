package concurrent;

import functions.ConstantFunction;
import functions.LinkedListTabulatedFunction;

public class ReadWriteTaskExecutor {
    public static void main(String[] args) {
        LinkedListTabulatedFunction func = new LinkedListTabulatedFunction(new ConstantFunction(-1), 1, 1000, 1000);
        Thread thr1 = new Thread(new ReadTask(func));
        Thread thr2 = new Thread(new WriteTask(func, 0.5));
        thr2.start();
        thr1.start();
    }
}
