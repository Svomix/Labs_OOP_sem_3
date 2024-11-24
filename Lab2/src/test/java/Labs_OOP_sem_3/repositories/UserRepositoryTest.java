package Labs_OOP_sem_3.repositories;

import Labs_OOP_sem_3.App.Application;
import Labs_OOP_sem_3.entities.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private UserEntity user;
    @BeforeEach
    public void createUser() {
        user = UserEntity.builder().password("3").username("2").build();
        userRepository.save(user);
    }
    @Test
    public void createTest() {
        Assertions.assertNotNull(userRepository.findById(user.getId()));
    }
    @Test
    public void findByUsername() {
        var u = userRepository.findByUsername(user.getUsername());
        Assertions.assertEquals(user.getId(), u.get().getId());
    }
    @Test
    public void noFindByUsername()
    {
        var u = userRepository.findByUsername("100");
        Assertions.assertTrue(u.isEmpty());
    }
    @AfterEach
    public void deleteUser() {
        userRepository.deleteById(user.getId());
    }
}
