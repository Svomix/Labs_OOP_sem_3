package Labs_OOP_sem_3.repositories;
import Labs_OOP_sem_3.entities.FunctionEntity;
import Labs_OOP_sem_3.entities.User;
import Labs_OOP_sem_3.App.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest(classes = Application.class)
public class FunctionRepositoryTest
{
    @Autowired
    private FunctionRepository functionRepository;
    @Test
    public void createTest()
    {
        FunctionEntity function = new FunctionEntity(1,"asd",new ArrayList<>());
        functionRepository.save(function);
        Assertions.assertNotNull(functionRepository.findById(function.getId()));
    }
}
