package Labs_OOP_sem_3.repositories;

import Labs_OOP_sem_3.App.Application;
import Labs_OOP_sem_3.entities.FunctionEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
public class FunctionRepositoryTest {
    @Autowired
    private FunctionRepository functionRepository;
    private FunctionEntity function;

    @BeforeEach
    public void createFunction() {
        function = FunctionEntity.builder().hash("asd").build();
        functionRepository.save(function);
    }

    @Test
    public void createTest() {
        Assertions.assertNotNull(functionRepository.findById(function.getId()));
    }

    @Test
    public void FindByNameTest() {
        ;
        var f = functionRepository.findByHash(function.getHash());
        Assertions.assertEquals(function.getHash(), f.getHash());
    }

    @AfterEach
    public void deleteFunction() {
        functionRepository.deleteById(function.getId());
    }
}
