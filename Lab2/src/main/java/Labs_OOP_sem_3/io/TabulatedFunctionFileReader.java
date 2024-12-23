package Labs_OOP_sem_3.io;

import Labs_OOP_sem_3.functions.factory.ArrayTabulatedFunctionFactory;
import Labs_OOP_sem_3.functions.factory.LinkedListTabulatedFunctionFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TabulatedFunctionFileReader {
    public static void main(String[] args) {
        try (BufferedReader in1 = new BufferedReader(new FileReader("input/function"));
             BufferedReader in2 = new BufferedReader(new FileReader("input/function"))) {
            System.out.println(FunctionsIO.readTabulatedFunction(in1, new ArrayTabulatedFunctionFactory()));
            System.out.println(FunctionsIO.readTabulatedFunction(in2, new LinkedListTabulatedFunctionFactory()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
