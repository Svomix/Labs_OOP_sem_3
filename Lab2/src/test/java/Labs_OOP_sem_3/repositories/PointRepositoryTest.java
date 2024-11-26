package Labs_OOP_sem_3.repositories;

import Labs_OOP_sem_3.App.Application;
import Labs_OOP_sem_3.entities.FunctionEntity;
import Labs_OOP_sem_3.entities.PointEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest(classes = Application.class)
public class PointRepositoryTest {
    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private FunctionRepository functionRepository;
    private FunctionEntity func;
    @BeforeEach
    public void createFunction() {
    }
    @BeforeEach
    public void createfunc()
    {
        func = FunctionEntity.builder().name("12").build();
        functionRepository.save(func);
    }
    @Test
    public void createTest() {
        var pointEntity = PointEntity.builder().xValue(3.0).yValue(4.0).function(func).build();
        pointRepository.save(pointEntity);
        Assertions.assertNotNull(pointRepository.findById(pointEntity.getId()));
        pointRepository.deleteById(pointEntity.getId());
    }
    @Test
    public void findByFunctionTest() {
        pointRepository.save(PointEntity.builder().xValue(3.0).yValue(4.0).function(func).build());
        pointRepository.save(PointEntity.builder().xValue(3.0).yValue(4.0).function(func).build());
        pointRepository.save(PointEntity.builder().xValue(3.0).yValue(4.0).function(func).build());
        Assertions.assertEquals(3, pointRepository.findByFunction(func.getId()).size());
    }
    @AfterEach
    public void deleteFunction() {
        functionRepository.deleteById(func.getId());
    }
}
