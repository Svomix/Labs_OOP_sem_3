package dao;

import entities.FunctionEntity;
import entities.Modification_type;

public class MainDao {
    public static void main(String[] args) {
            FunctionDaoImpl functionDaoImpl = new FunctionDaoImpl();
            FunctionEntity functionEntity = FunctionEntity.builder()
                    .mod(Modification_type.usual)
                    .hash("23")
                    .xArr(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0})
                    .yArr(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0})
                    .build();
            functionDaoImpl.create(functionEntity);
            FunctionEntity functionEntity1 = functionDaoImpl.read("from Function where id = 3");
            functionEntity1.setHash("123");
            System.out.println(functionDaoImpl.update(functionEntity1));
            FunctionEntity functionEntity2 = functionDaoImpl.read("from Function where id = 3");
            functionDaoImpl.delete(functionEntity2);
    }
}
