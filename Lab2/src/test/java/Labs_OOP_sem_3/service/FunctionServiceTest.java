package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.App.Application;
import Labs_OOP_sem_3.dto.FunctionDtoList;
import Labs_OOP_sem_3.entities.PointEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static Labs_OOP_sem_3.convertos.ConvertorToFuncEntity.convert;

@SpringBootTest(classes = Application.class)
public class FunctionServiceTest {
    @Autowired
    private FunctionService functionService;
    private FunctionDtoList function;

    @BeforeEach
    public void createFunction() {
        functionService.updateSequence();
        function = FunctionDtoList.builder().id(1).hash("asd").points(new ArrayList<>()).build();
        functionService.create(function);
    }

    @Test
    void create() {
        Assertions.assertEquals(functionService.read(function.getId()).getHash(), function.getHash());
    }

    @Test
    void update() {
        var arrP = new ArrayList<PointEntity>();
        var updFunc = FunctionDtoList.builder().id(1).hash("0").points(arrP).build();
        arrP.add(PointEntity.builder().function(convert(updFunc)).xValue(1).yValue(2).build());
        arrP.add(PointEntity.builder().function(convert(updFunc)).xValue(3).yValue(4).build());
        functionService.update(updFunc);
        Assertions.assertEquals(updFunc.getHash(), functionService.read(function.getId()).getHash());

    }

    @Test
    void delete() {
        var updFunc = FunctionDtoList.builder().id(1).hash("123").points(new ArrayList<>()).build();
        functionService.create(updFunc);
        functionService.delete(updFunc);
        Assertions.assertNull(functionService.read(updFunc.getId()));
    }

    @Test
    void read() {
        var updFunc = FunctionDtoList.builder().id(1).points(new ArrayList<>()).hash("123").build();
        functionService.create(updFunc);
        Assertions.assertEquals(functionService.read(updFunc.getId()).getHash(), updFunc.getHash());
        Assertions.assertNull(functionService.read(updFunc.getId() + 1));

    }

    @Test
    void readByName() {
        Assertions.assertEquals(functionService.readByName(function.getHash()).getId(), function.getId());
        Assertions.assertNull(functionService.readByName(function.getHash() + 1));
    }

    @AfterEach
    @Transactional
    public void destroy() {
        functionService.delete(function);
    }
}