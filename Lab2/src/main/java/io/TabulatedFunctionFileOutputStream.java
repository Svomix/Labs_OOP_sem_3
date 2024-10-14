package io;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TabulatedFunctionFileOutputStream
{
    public static void main(String[] args) throws FileNotFoundException {
        ArrayTabulatedFunction arr = new ArrayTabulatedFunction(new double[]{1,2,3}, new double[]{1,2,3});
        LinkedListTabulatedFunction list = new LinkedListTabulatedFunction(new double[]{1,2,3},new double[]{1,2,3});
        try(BufferedOutputStream bosarr = new BufferedOutputStream(new FileOutputStream("output/array function.bin"));
        BufferedOutputStream boslist = new BufferedOutputStream(new FileOutputStream("output/linked list function.bin")))
        {
        FunctionsIO.writeTabulatedFunction(bosarr,arr);
        FunctionsIO.writeTabulatedFunction(boslist,list);
        }
        catch (IOException excep)
        {
            excep.printStackTrace();
        }
    }
}
