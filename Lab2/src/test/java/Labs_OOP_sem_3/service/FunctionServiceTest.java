package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.App.Application;
import Labs_OOP_sem_3.dto.FunctionDto;
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
    private FunctionDto function;

    @BeforeEach
    public void createFunction() {
        functionService.updateSequence();
        function = FunctionDto.builder().id(1).name("asd").points(new ArrayList<>()).build();
        functionService.create(function);
    }

    @Test
    void create() {
        Assertions.assertEquals(functionService.read(function.getId()).getName(), function.getName());
    }

    @Test
    void update() {
        var arrP = new ArrayList<PointEntity>();
        var updFunc = FunctionDto.builder().id(1).name("0").points(arrP).build();
        arrP.add(PointEntity.builder().function(convert(updFunc)).xValue(1).yValue(2).build());
        arrP.add(PointEntity.builder().function(convert(updFunc)).xValue(3).yValue(4).build());
        functionService.update(updFunc);
        Assertions.assertEquals(updFunc.getName(), functionService.read(function.getId()).getName());

    }

    @Test
    void delete() {
        var updFunc = FunctionDto.builder().id(1).name("123").points(new ArrayList<>()).build();
        functionService.create(updFunc);
        functionService.delete(updFunc);
        Assertions.assertNull(functionService.read(updFunc.getId()));
    }

    @Test
    void read() {
        var updFunc = FunctionDto.builder().id(1).points(new ArrayList<>()).name("123").build();
        functionService.create(updFunc);
        Assertions.assertEquals(functionService.read(updFunc.getId()).getName(), updFunc.getName());
        Assertions.assertNull(functionService.read(updFunc.getId() + 1));

    }

    @Test
    void readByName() {
        Assertions.assertEquals(functionService.readByName(function.getName()).getId(), function.getId());
        Assertions.assertNull(functionService.readByName(function.getName() + 1));
    }

    @AfterEach
    @Transactional
    public void destroy() {
        functionService.delete(function);
    }
}