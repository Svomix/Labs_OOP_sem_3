package dao;

import entities.Function;
import entities.Modification_type;
import util.HibernateUtil;

public class MainDao {
    public static void main(String[] args) {
            FunctionDao functionDao = new FunctionDao();
            Function function = Function.builder()
                    .mod(Modification_type.usual)
                    .hash("23")
                    .xArr(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0})
                    .yArr(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0})
                    .build();
            functionDao.create(function);
            Function function1 = functionDao.read("from Function where id = 3");
            function1.setHash("123");
            System.out.println(functionDao.update(function1));
            Function function2 = functionDao.read("from Function where id = 3");
            functionDao.delete(function2);
    }
}
