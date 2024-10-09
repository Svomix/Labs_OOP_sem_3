package io;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TabulatedFunctionFileWriter {
    public static void main(String[] args) throws IOException {
        try (BufferedWriter out1 = new BufferedWriter(new FileWriter("output/array function"));
             BufferedWriter out2 = new BufferedWriter(new FileWriter("output/linked list function"))){
            ArrayTabulatedFunction function1 = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
            LinkedListTabulatedFunction function2 = new LinkedListTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
            FunctionsIO.writeTabulatedFunction(out1, function1);
            FunctionsIO.writeTabulatedFunction(out2, function2);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}