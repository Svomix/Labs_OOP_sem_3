package Labs_OOP_sem_3.io;

import Labs_OOP_sem_3.functions.ArrayTabulatedFunction;
import Labs_OOP_sem_3.functions.Point;
import Labs_OOP_sem_3.functions.TabulatedFunction;
import Labs_OOP_sem_3.functions.factory.TabulatedFunctionFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public final class FunctionsIO {
    private FunctionsIO() {
        throw new UnsupportedOperationException("Functions cannot be instantiated");
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

    static TabulatedFunction readTabulatedFunction(BufferedInputStream inputStream, TabulatedFunctionFactory factory) throws IOException {
        var binput = new DataInputStream(inputStream);
        int size = binput.readInt();
        double[] xValues = new double[size];
        double[] yValues = new double[size];
        for (int i = 0; i < size; ++i) {
            xValues[i] = binput.readDouble();
            yValues[i] = binput.readDouble();
        }
        return factory.create(xValues, yValues);
    }


    static void serialize(BufferedOutputStream stream, TabulatedFunction function) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(stream);
        out.writeObject(function);
        out.flush();
    }

    static void writeTabulatedFunction(BufferedOutputStream outputStream, TabulatedFunction function) throws IOException {
        var outp = new DataOutputStream(outputStream);
        outp.writeInt(function.getCount());
        for (Point p : function) {
            outp.writeDouble(p.x);
            outp.writeDouble(p.y);
        }
        outp.flush();
    }

    static void serializeXml(BufferedWriter writer, ArrayTabulatedFunction function) throws IOException {
        XStream xStream = new XStream();
        writer.write(xStream.toXML(function));
        writer.flush();
    }

    static ArrayTabulatedFunction deserializeXml(BufferedReader reader) {
        XStream xStream = new XStream();
        xStream.allowTypeHierarchy(ArrayTabulatedFunction.class);
        return (ArrayTabulatedFunction) xStream.fromXML(reader);
    }

    static TabulatedFunction deserialize(BufferedInputStream stream) throws IOException, ClassNotFoundException {
        var inp = new ObjectInputStream(stream);
        return (TabulatedFunction) inp.readObject();
    }

    static void serializeJson(BufferedWriter writer, ArrayTabulatedFunction function) throws IOException {
        var objmap = new ObjectMapper().writeValueAsString(function);
        writer.write(objmap);
        writer.flush();
    }

    static ArrayTabulatedFunction deserializeJson(BufferedReader reader) throws IOException {
        var objmap = new ObjectMapper();
        return (ArrayTabulatedFunction) objmap.readerFor(ArrayTabulatedFunction.class).readValue(reader);
    }

}
