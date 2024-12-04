package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.App.Application;
import Labs_OOP_sem_3.entities.UserEntity;
import Labs_OOP_sem_3.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = Application.class)
@Transactional
class UserServiceTest {
    private UserEntity userEntity;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder()
                .id(31L)
                .username("name")
                .password("password")
                .build();
        userRepository.save(userEntity);
    }

    @Test
    void findUserByUsername() {
        UserEntity user = userRepository.findByUsername("name").orElse(null);
        assertNotNull(user);
        assertEquals(userEntity.getUsername(), user.getUsername());
        assertEquals(userEntity.getPassword(), user.getPassword());
    }

    @Test
    void findUserById() {
        UserEntity user = userRepository.findById(2L).orElse(null);
        assertNotNull(user);
        assertEquals(userEntity.getUsername(), user.getUsername());
        assertEquals(userEntity.getPassword(), user.getPassword());
    }

    @AfterEach
    void tearDown() {
        userRepository.delete(userEntity);
    }
}