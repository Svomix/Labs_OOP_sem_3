package entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FunctionEntityTest {
    private static FunctionEntity function;

    @BeforeAll
    static void setUpBeforeClass() {
        function = new FunctionEntity(1, Modification_type.usual, "123", new Double[]{1.0, 2.0, 3.0}, new Double[]{1.0, 2.0, 3.0});
    }

    @Test
    void getId() {
        Assertions.assertEquals(1, function.getId());
    }

    @Test
    void getMod() {
        Assertions.assertEquals(Modification_type.usual, function.getMod());
    }

    @Test
    void getHash() {
        Assertions.assertEquals("123", function.getHash()); //Подставить util класс для поиска хэша
    }

    @Test
    void getXArr() {
        Assertions.assertArrayEquals(new Double[]{1.0, 2.0, 3.0}, function.getXArr());
    }

    @Test
    void getYArr() {
        Assertions.assertArrayEquals(new Double[]{1.0, 2.0, 3.0}, function.getYArr());
    }

    @Test
    void setId() {
        function.setId(2);
        Assertions.assertEquals(2, function.getId());
    }

    @Test
    void setMod() {
        function.setMod(Modification_type.usual);
        Assertions.assertEquals(Modification_type.usual, function.getMod());
    }

    @Test
    void setHash() {
        function.setHash("31");
        Assertions.assertEquals("31", function.getHash());
    }

    @Test
    void setXArr() {
        function.setXArr(new Double[]{1.0, 2.0, 4.0});
        Assertions.assertArrayEquals(new Double[]{1.0, 2.0, 4.0}, function.getXArr());
    }

    @Test
    void setYArr() {
        function.setYArr(new Double[]{1.0, 2.0, 4.0});
        Assertions.assertArrayEquals(new Double[]{1.0, 2.0, 4.0}, function.getYArr());
    }

    @Test
    void testEquals() {
        function = new FunctionEntity(1, Modification_type.usual, "123", new Double[]{1.0, 2.0, 3.0}, new Double[]{1.0, 2.0, 3.0});
        FunctionEntity func = FunctionEntity.builder().
                id(1).
                mod(Modification_type.usual)
                .hash("123")
                .xArr(new Double[]{1.0, 2.0, 3.0})
                .yArr(new Double[]{1.0, 2.0, 3.0})
                .build();
        Assertions.assertEquals(func, function);
    }

    @Test
    void testHashCode() {
        function = new FunctionEntity(1, Modification_type.usual, "123", new Double[]{1.0, 2.0, 3.0}, new Double[]{1.0, 2.0, 3.0});
        FunctionEntity func = FunctionEntity.builder().
                id(1).
                mod(Modification_type.usual)
                .hash("123")
                .xArr(new Double[]{1.0, 2.0, 3.0})
                .yArr(new Double[]{1.0, 2.0, 3.0})
                .build();
        Assertions.assertEquals(func.hashCode(), function.hashCode());
    }

    @Test
    void testToString() {
        FunctionEntity func = FunctionEntity.builder().
                id(1).
                mod(Modification_type.usual)
                .hash("123")
                .xArr(new Double[]{1.0, 2.0, 3.0})
                .yArr(new Double[]{1.0, 2.0, 3.0})
                .build();
        Assertions.assertEquals(func.toString(), function.toString());
    }

}