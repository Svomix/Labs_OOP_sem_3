package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.App.Application;
import Labs_OOP_sem_3.entities.UserEntity;
import Labs_OOP_sem_3.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = Application.class)
public class UserServiceTest
{
    @Autowired
    UserService userService;
    private UserDetails user;
    @BeforeEach
    void create()
    {
        user = User.builder().username("abc").password("123").build();
        userService.createUser(user);
    }
    @Test
    void loadUserByUsername()
    {
        Assertions.assertNotNull(userService.loadUserByUsername("abc"));
    }

    @Test
    void createUser()
    {

    }

    @Test
    void updateUser()
    {

    }
    @Test
    void deleteUser()
    {

    }

    @Test
    void changePassword()
    {

    }

    @Test
    void userExists()
    {

    }

    @Test
    void findUserByUsername()
    {

    }

    @Test
    void findUserById()
    {

    }
    @AfterEach
    @Transactional
    void destroy()
    {
        userService.deleteUser(user.getUsername());
        userService.updateSequence();
    }
}
