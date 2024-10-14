package io;

import functions.ArrayTabulatedFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;


class SerializedXMLTest {
    @Test
    void test() throws IOException {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{4, 5, 6});
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        FunctionsIO.serializeXml(writer, function);

        StringReader stringReader = new StringReader(stringWriter.toString());
        BufferedReader reader = new BufferedReader(stringReader);
        ArrayTabulatedFunction deserializedFunction = FunctionsIO.deserializeXml(reader);

        Assertions.assertEquals(function.toString(), deserializedFunction.toString());
    }

}