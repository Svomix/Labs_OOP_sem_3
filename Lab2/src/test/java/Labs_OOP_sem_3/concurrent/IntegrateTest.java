package Labs_OOP_sem_3.concurrent;


import Labs_OOP_sem_3.concurrent.Integrate;
import Labs_OOP_sem_3.functions.ArrayTabulatedFunction;
import org.junit.jupiter.api.Test;

class IntegrateTest {

    @Test
    void test() throws InterruptedException {
        Integrate integrate = new Integrate(new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{6, 7, 8, 9, 10}));
        System.out.println(integrate.SimpsonMethod());
    }

}