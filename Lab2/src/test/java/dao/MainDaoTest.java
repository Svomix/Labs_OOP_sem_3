package dao;

import entities.FunctionEntity;
import entities.PointEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


class MainDaoTest {
    private static final FunctionDaoImpl functionDaoImpl = new FunctionDaoImpl();

    @BeforeAll
    static void tearDown() {
        PointEntity pointEntity = PointEntity.builder()
                .build();
        FunctionEntity functionEntity = FunctionEntity.builder()
                .functionType("linear")
                .points(new ArrayList<>(){})
                .build();
        functionDaoImpl.create(functionEntity);
    }

    @Test
    void createTest() {
        FunctionEntity functionEntity = FunctionEntity.builder()
                .hash(5451)
                .xArr(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0})
                .yArr(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0, 7.0})
                .build();
        Assertions.assertEquals(functionEntity, functionDaoImpl.create(functionEntity));
        FunctionEntity functionEntityReal = FunctionEntity.builder()
                .hash("545521")
                .xArr(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0})
                .yArr(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0, 7.0})
                .build();
        FunctionEntity functionEntity2 = functionDaoImpl.read("""
                select * from labs.public.functions
                where hash = '545521'
                """);
        Assertions.assertEquals(functionEntityReal, functionEntity2);
        FunctionEntity functionEntity3 = functionDaoImpl.read("""
                select * from labs.public.functions
                where hash = '545521'
                """);
        functionEntity3.setMod(Modification_type.strictunmodifiable);
        functionDaoImpl.update(functionEntity3);
        Assertions.assertEquals(functionEntity3, functionDaoImpl.read("""
                select * from labs.public.functions
                where hash = '545521'
                """));
    }


    @AfterAll
    static void tearDown2() {
        FunctionEntity functionEntity = functionDaoImpl.read("""
                select * from labs.public.functions
                where hash = '5451'
                """);
        FunctionEntity functionEntity2 = functionDaoImpl.read("""
                select * from labs.public.functions
                where hash = '545521'
                """);

        functionDaoImpl.delete(functionEntity);
        functionDaoImpl.delete(functionEntity2);
    }

}