package Labs_OOP_sem_3.io;

import Labs_OOP_sem_3.functions.ArrayTabulatedFunction;

import java.io.*;

public class ArrayTabulatedFunctionJson {
    public static void main(String[] args) throws IOException {
        try (var out = new BufferedWriter(new FileWriter("output/serialized array functions.json"))) {
            ArrayTabulatedFunction arr = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4}, new double[]{1, 4, 9, 16});
            FunctionsIO.serializeJson(out, arr);
        } catch (IOException excep) {
            excep.printStackTrace();
        }
        try (var inp = new BufferedReader(new FileReader("output/serialized array functions.json"));) {

            ArrayTabulatedFunction arr1 = FunctionsIO.deserializeJson(inp);
            System.out.println(arr1);
        } catch (IOException excep) {
            excep.printStackTrace();
        }
    }
}
