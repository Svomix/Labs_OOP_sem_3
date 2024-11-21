package Labs_OOP_sem_3.repositories;

import Labs_OOP_sem_3.App.Application;
import Labs_OOP_sem_3.entities.FunctionEntity;
import Labs_OOP_sem_3.entities.PointEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest(classes = Application.class)
public class PointRepositoryTest {
    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private FunctionRepository functionRepository;

    @Test
    public void createTest() {
        var func = new FunctionEntity(1, "12", new ArrayList<>());
        var pointEntity = new PointEntity(1, func, 3.0, 4.0);
        func.setPoints(Arrays.asList(pointEntity));
        pointRepository.save(pointEntity);
        Assertions.assertNotNull(pointRepository.findById(1));
    }

    @Test
    public void findByFunctionTest() {


        ArrayList<PointEntity> list = new ArrayList<>();
        var func = new FunctionEntity(140, "12", list);
        list.add(new PointEntity(1, func, 3.0, 4.0));
        list.add(new PointEntity(2, func, 4.0, 5.0));
        list.add(new PointEntity(3, func, 5.0, 6.0));
        functionRepository.save(func);

        Assertions.assertEquals(3, pointRepository.findByFunction(func.getId()).size());
    }
}
