package Labs_OOP_sem_3.repositories;
import Labs_OOP_sem_3.entities.UserEntity;
import Labs_OOP_sem_3.App.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
public class UserRepositoryTest
{
    @Autowired
    private UserRepository userRepository;
    @Test
    public void createTest()
    {
        UserEntity user = new UserEntity(1,"2","3");
        userRepository.save(user);
        Assertions.assertNotNull(userRepository.findById(user.getId()));
    }
    @Test
    public void findByUsername()
    {
        UserEntity user = new UserEntity(1,"2","3");
        userRepository.save(user);
        var u = userRepository.findByUsername(user.getUsername());
        Assertions.assertEquals(user.getId(),u.get().getId());
    }
}
