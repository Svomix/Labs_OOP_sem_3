package io;


import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.Point;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import operations.TabulatedDifferentialOperator;
import operations.TabulatedFunctionOperationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeClass;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class FunctionsIOTest {
    @BeforeClass
    void delete() throws IOException {
        Path path = Paths.get("temp/test1");
        Files.delete(path);
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
    void readTabulatedFunctionTest2() throws IOException {
        try (var breader = new BufferedReader(new FileReader("temp/test2"))) {
            var list = FunctionsIO.readTabulatedFunction(breader, new LinkedListTabulatedFunctionFactory());
            Assertions.assertArrayEquals(new Point[]{new Point(4, 7), new Point(5, 8), new Point(6, 9)}, TabulatedFunctionOperationService.asPoint(list));
        } catch (IOException excep) {
            excep.printStackTrace();
        }
    }

    @Test
    void serialize() {

    }

    @Test
    void writeTabulatedFunction() throws IOException {
        try (var bwrite = new BufferedWriter(new FileWriter("temp/test2"))) {
            var list = new LinkedListTabulatedFunction(new double[]{4, 5, 6}, new double[]{7, 8, 9});
            FunctionsIO.writeTabulatedFunction(bwrite, list);
        } catch (IOException excep) {
            excep.printStackTrace();
        }
    }

    @Test
    void serializeXml() {
    }

    @Test
    void deserializeXml() {
    }

    @Test
    void deserialize() {
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


