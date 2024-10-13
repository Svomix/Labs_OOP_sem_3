package io;


import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import org.apache.commons.io.FileUtils;
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
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("temp/test1"))) {
            FunctionsIO.writeTabulatedFunction(bw,
                    new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{4, 5, 6}));
        }
    }

    @Test
    void readTabulatedFunctionTest() throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader("temp/test1"))) {
            TabulatedFunction function = FunctionsIO.readTabulatedFunction(br, new ArrayTabulatedFunctionFactory());
            Assertions.assertEquals(new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{4, 5, 6}).toString(), function.toString());
        }
    }

}