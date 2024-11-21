package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.App.Application;
import Labs_OOP_sem_3.entities.UserEntity;
import Labs_OOP_sem_3.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
public class UserServiceTest
{
    @Autowired
    private UserService userService;
    @Test
    public void LoadTest()
    {
        var a = new UserEntity(1,"asd","123");
        userService.createUser(a);
        var b = userService.loadUserByUsername(a.getUsername());
        Assertions.assertEquals(a.getUsername(), b.getUsername());
    }
}
