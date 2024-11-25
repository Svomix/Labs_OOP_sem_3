
package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.App.Application;
import Labs_OOP_sem_3.dto.FunctionDto;
import Labs_OOP_sem_3.repositories.FunctionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@SpringBootTest(classes = Application.class)
public class FunctionServiceTest {
    @Autowired
    private FunctionService functionService;
    private FunctionDto function;
    @BeforeEach
    public void createFunction() {
        functionService.updateSequence();
        function = FunctionDto.builder().id(1).name("asd").build();
        functionService.create(function);
    }

    @Test
    void create() {
       Assertions.assertEquals(functionService.read(function.getId()).getName(), function.getName());
    }

    @Test
    void update() {
        var updFunc = FunctionDto.builder().id(1).name("123").build();
        functionService.update(updFunc);
        Assertions.assertEquals(updFunc.getName(), functionService.read(function.getId()).getName());
    }
    @Test
    void delete()
    {
        var updFunc = FunctionDto.builder().id(1).name("123").build();
        functionService.create(updFunc);
        functionService.delete(updFunc);
        Assertions.assertNull(functionService.read(updFunc.getId()));
    }

    @Test
    void read() {
        var updFunc = FunctionDto.builder().id(1).name("123").build();
        functionService.create(updFunc);
        Assertions.assertEquals(functionService.read(updFunc.getId()).getName(),updFunc.getName());
        Assertions.assertNull(functionService.read(updFunc.getId() + 1));

    }
    @Test
    void readByName() {
        Assertions.assertEquals(functionService.readByName(function.getName()).getId(),function.getId());
        Assertions.assertNull(functionService.readByName(function.getName() + 1));
    }
    @AfterEach
    @Transactional
    public void destroy() {
        functionService.delete(function);
    }
}