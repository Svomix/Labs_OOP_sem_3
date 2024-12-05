package Labs_OOP_sem_3.controllers;

import Labs_OOP_sem_3.App.Application;
import Labs_OOP_sem_3.entities.UserEntity;
import Labs_OOP_sem_3.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private UserEntity user;

    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void setUp() throws Exception {
        user = UserEntity.builder().username("Danil").password("12345").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().
                writeValueAsString(user))).andExpect(MockMvcResultMatchers.status().isOk());
        encoder = new BCryptPasswordEncoder();
    }

    @Test
    @WithMockUser
    void getUserByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/Danil").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().
                writeValueAsString(user))).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void loginUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/login").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().
                writeValueAsString(user))).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @AfterEach
    @WithMockUser
    void delete() throws Exception {
        userRepository.deleteById(userRepository.findByUsername(user.getUsername()).get().getId());
    }
}