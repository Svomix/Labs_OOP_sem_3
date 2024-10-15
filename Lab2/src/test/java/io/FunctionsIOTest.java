package io;


import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.Point;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import operations.TabulatedDifferentialOperator;
import operations.TabulatedFunctionOperationService;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class FunctionsIOTest{
    @AfterAll
    public static void delete() throws IOException {

        //File f = new File("temp/test1");
        //File f2 = new File("temp/test2");
        //f.delete();
        //f2.delete();
        //FileUtils.cleanDirectory(new File("E:/Programs/JetBrains/IdeaProjects/Labs_OOP_sem_3/Lab2/temp/"));
        FileUtils.cleanDirectory(new File("temp/"));
    }

    @BeforeAll
    public static void setUp() throws IOException {
        File f = new File("temp/test1");
        File f2 = new File("temp/test2");
        f.createNewFile();
        f2.createNewFile();
    }

    @Test
    void writeTabulatedFunctionTest() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("temp/test1"))) {
            FunctionsIO.writeTabulatedFunction(bw,
                    new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{4, 5, 6}));
        }
    }

    @Test
    void readTabulatedFunctionTest1() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("temp/test1"))) {
            TabulatedFunction function = FunctionsIO.readTabulatedFunction(br, new ArrayTabulatedFunctionFactory());
            Assertions.assertEquals(new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{4, 5, 6}).toString(), function.toString());
        }
    }

    @Test
    void writeTabulatedFunctionTest2() throws IOException {
        try (var outputf = new BufferedOutputStream(new FileOutputStream("temp/test2"))) {
            var list = new LinkedListTabulatedFunction(new double[]{4, 5, 6}, new double[]{7, 8, 9});
            FunctionsIO.writeTabulatedFunction(outputf, list);
        } catch (IOException excep) {
            excep.printStackTrace();
        }
    }

    @Test
    void readTabulatedFunctionTest2() throws IOException {
        try (var breader = new BufferedInputStream(new FileInputStream("temp/test2"))) {
            var list = FunctionsIO.readTabulatedFunction(breader, new LinkedListTabulatedFunctionFactory());
            Assertions.assertArrayEquals(new Point[]{new Point(4, 7), new Point(5, 8), new Point(6, 9)}, TabulatedFunctionOperationService.asPoint(list));
        } catch (IOException excep) {
            excep.printStackTrace();
        }
    }

    @Test
    void serialize() {
        try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("output/serialized array functions.bin"))) {
            ArrayTabulatedFunction arr = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4}, new double[] {1, 2, 3, 4});
            TabulatedFunction first = new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory()).derive(arr);
            TabulatedFunction second = new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory()).derive(first);
            FunctionsIO.serialize(bos, arr);
            FunctionsIO.serialize(bos, first);
            FunctionsIO.serialize(bos, second);
            try(BufferedInputStream bi = new BufferedInputStream(new FileInputStream("output/serialized array functions.bin"))) {
                Assertions.assertEquals(FunctionsIO.deserialize(bi).toString(), arr.toString());
                Assertions.assertEquals(FunctionsIO.deserialize(bi).toString(), first.toString());
                Assertions.assertEquals(FunctionsIO.deserialize(bi).toString(), second.toString());
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void serializeJson() throws IOException {
        try (var bwrite = new BufferedWriter(new FileWriter("temp/serialized_arr.json"))) {
            var arr = new ArrayTabulatedFunction(new double[]{4, 5, 6}, new double[]{7, 8, 9});
            FunctionsIO.serializeJson(bwrite, arr);
        } catch (IOException excep) {
            excep.printStackTrace();
        }
    }

    @Test
    void deserializeJson() throws IOException {
        try (var breader = new BufferedReader(new FileReader("temp/serialized_arr.json"))) {
            var arr = FunctionsIO.deserializeJson(breader);
            Assertions.assertArrayEquals(new Point[]{new Point(4, 7), new Point(5, 8), new Point(6, 9)}, TabulatedFunctionOperationService.asPoint(arr));
        } catch (IOException excep) {
            excep.printStackTrace();
        }
    }
}


