package io;

import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.LinkedListTabulatedFunctionFactory;
import operations.TabulatedDifferentialOperator;

import java.io.*;

public class LinkedListTabulatedFunctionSerialization
{
    public static void main(String[] args) throws IOException{
        try(var out = new BufferedOutputStream(new FileOutputStream("output/serialized linked list functions.bin"));) {
            LinkedListTabulatedFunction list = new LinkedListTabulatedFunction(new double[]{2,3,4,5},new double[]{8,27,64,125});
            TabulatedDifferentialOperator op1 = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
            TabulatedFunction listdif1 = op1.derive(list);
            TabulatedDifferentialOperator op2 = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
            TabulatedFunction listdif2 = op2.derive(listdif1);
            FunctionsIO.serialize(out,list);
            FunctionsIO.serialize(out,listdif1);
            FunctionsIO.serialize(out,listdif2);
        }
        catch (IOException excep)
        {
            excep.printStackTrace();
        }
        try(var out = new BufferedInputStream(new FileInputStream("output/serialized linked list functions.bin"));) {
            TabulatedFunction func1 = FunctionsIO.deserialize(out);
            TabulatedFunction func2 = FunctionsIO.deserialize(out);
            TabulatedFunction func3 = FunctionsIO.deserialize(out);
            System.out.println(func1);
            System.out.println(func2);
            System.out.println(func3);
        }
        catch (IOException|ClassNotFoundException excep)
        {
            excep.printStackTrace();
        }
    }
}
