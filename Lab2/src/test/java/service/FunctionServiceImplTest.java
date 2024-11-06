package service;

import entities.FunctionEntity;
import entities.Modification_type;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FunctionServiceImplTest {
    private static final FunctionService functionService = new FunctionServiceImpl();

    @BeforeAll
    static void tearDown() {
        FunctionEntity functionEntity = FunctionEntity.builder().id(2)
                .mod(Modification_type.usual)
                .hash("541")
                .xArr(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0})
                .yArr(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0, 7.0})
                .build();
        functionService.create(functionEntity);
    }

    @Test
    void create() {
        FunctionEntity functionEntity = FunctionEntity.builder()
                .mod(Modification_type.strict)
                .hash("545")
                .xArr(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0})
                .yArr(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0, 7.0})
                .build();
        Assertions.assertEquals(functionEntity, functionService.create(functionEntity));
        FunctionEntity functionEntityReal = FunctionEntity.builder()
                .mod(Modification_type.usual)
                .hash("541")
                .xArr(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0})
                .yArr(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0, 7.0})
                .build();
        FunctionEntity functionEntity2 = functionService.read("""
                select * from labs.public.functions
                where hash = '541'
                """);
        Assertions.assertEquals(functionEntityReal, functionEntity2);
        FunctionEntity functionEntity3 = functionService.read("""
                select * from labs.public.functions
                where hash = '541'
                """);
        functionEntity3.setMod(Modification_type.strictunmodifiable);
        functionService.update(functionEntity3);
        Assertions.assertEquals(functionEntity3, functionService.read("""
                select * from labs.public.functions
                where hash = '541'
                """));
    }


    @AfterAll
    static void tearDown2() {
        FunctionEntity functionEntity = functionService.read("""
                select * from labs.public.functions
                where hash = '545'
                """);
        FunctionEntity functionEntity2 = functionService.read("""
                select * from labs.public.functions
                where hash = '541'
                """);

        functionService.delete(functionEntity);
        functionService.delete(functionEntity2);
    }
}