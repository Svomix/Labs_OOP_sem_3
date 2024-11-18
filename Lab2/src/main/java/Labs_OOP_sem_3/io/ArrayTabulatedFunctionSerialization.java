package Labs_OOP_sem_3.io;

import Labs_OOP_sem_3.functions.ArrayTabulatedFunction;
import Labs_OOP_sem_3.functions.TabulatedFunction;
import Labs_OOP_sem_3.functions.factory.ArrayTabulatedFunctionFactory;
import Labs_OOP_sem_3.operations.TabulatedDifferentialOperator;

import java.io.*;

public class ArrayTabulatedFunctionSerialization {
    public static void main(String[] args) {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("output/serialized array functions.bin"))) {
            ArrayTabulatedFunction arr = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4}, new double[]{1, 2, 3, 4});
            TabulatedFunction first = new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory()).derive(arr);
            TabulatedFunction second = new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory()).derive(first);
            FunctionsIO.serialize(bos, arr);
            FunctionsIO.serialize(bos, first);
            FunctionsIO.serialize(bos, second);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedInputStream bos = new BufferedInputStream(new FileInputStream("output/serialized array functions.bin"))) {
            System.out.println(FunctionsIO.deserialize(bos));
            System.out.println(FunctionsIO.deserialize(bos));
            System.out.println(FunctionsIO.deserialize(bos));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
