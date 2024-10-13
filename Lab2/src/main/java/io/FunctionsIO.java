package io;

import com.thoughtworks.xstream.XStream;
import functions.ArrayTabulatedFunction;
import functions.Point;
import functions.TabulatedFunction;
import functions.factory.TabulatedFunctionFactory;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public final class FunctionsIO {
    private FunctionsIO() {
        throw new UnsupportedOperationException("Fuctions cannot be instantiated");
    }

    static void writeTabulatedFunction(BufferedWriter writer, TabulatedFunction function) {
        PrintWriter out = new PrintWriter(writer);
        out.println(function.getCount());
        for (Point point : function) {
            out.printf("%f %f\n", point.x, point.y);
        }
        out.flush();
    }

    static TabulatedFunction readTabulatedFunction(BufferedReader reader, TabulatedFunctionFactory factory) throws IOException {
        int size = Integer.parseInt(reader.readLine());
        double[] xValues = new double[size];
        double[] yValues = new double[size];
        NumberFormat formatter = NumberFormat.getInstance(Locale.forLanguageTag("ru"));
        for (int i = 0; i < size; i++) {
            String line = reader.readLine();
            String[] values = line.split(" ");
            try {
                xValues[i] = formatter.parse(values[0]).doubleValue();
                yValues[i] = formatter.parse(values[1]).doubleValue();
            } catch (ParseException e) {
                throw new IOException(e);
            }
        }
        return factory.create(xValues, yValues);
    }

    static void serializable(BufferedOutputStream stream, TabulatedFunction function) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(stream);
        out.writeObject(function);
        out.flush();
    }

    static TabulatedFunction deserializable(BufferedInputStream stream) throws IOException, ClassNotFoundException {
        return new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{1, 2, 3}); // signature
    }
   static void writeTabulatedFunction(BufferedOutputStream outputStream, TabulatedFunction function) throws IOException {
        var outp = new DataOutputStream(outputStream);
        outp.writeInt(function.getCount());
        for (Point p: function)
        {
            outp.writeDouble(p.x);
            outp.writeDouble(p.y);
        }
        outp.flush();
   }
    static void serializeXml(BufferedWriter writer, ArrayTabulatedFunction function){
        XStream xStream = new XStream();
        xStream.toXML(function, writer);
    }
    static ArrayTabulatedFunction deserializeXml(BufferedReader reader){
        XStream xStream = new XStream();
        return (ArrayTabulatedFunction) xStream.fromXML(reader);
    }
}
