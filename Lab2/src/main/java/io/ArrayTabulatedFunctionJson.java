package io;

import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import operations.TabulatedDifferentialOperator;

import java.io.*;

public class ArrayTabulatedFunctionJson
{
    public static void main(String[] args) throws FileNotFoundException {
        try {
            var out = new BufferedWriter(new FileWriter("output/serialized array functions.json") {
            });
            ArrayTabulatedFunction arr = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4}, new double[] {1, 4, 9, 16});
            ArrayTabulatedFunction firstf = (ArrayTabulatedFunction) new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory()).derive(arr);
            FunctionsIO.serializeJson(out,arr);
            FunctionsIO.serializeJson(out,firstf);
        }catch (IOException excep)
        {
            excep.printStackTrace();
        }
        try {
            var inp = new BufferedReader(new FileReader("output/serialized array functions.json"));
            ArrayTabulatedFunction arr1 =  FunctionsIO.deserializeJson(inp);
            ArrayTabulatedFunction arr2 =  FunctionsIO.deserializeJson(inp);
            System.out.println(arr1);
            System.out.println(arr2);
        } catch (IOException excep) {
            excep.printStackTrace();
        }
    }
}
