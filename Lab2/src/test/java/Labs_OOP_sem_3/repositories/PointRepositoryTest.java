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
public class PointRepositoryTest
{
    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private FunctionRepository functionRepository;
    @Test
    public void createTest()
    {
        var func = new FunctionEntity(1,"12",new ArrayList<>());
        var pointEntity = new PointEntity(1,func,3.0,4.0);
        func.setPoints(Arrays.asList(pointEntity));
        pointRepository.save(pointEntity);
        Assertions.assertNotNull(pointRepository.findById(1));
    }
}
