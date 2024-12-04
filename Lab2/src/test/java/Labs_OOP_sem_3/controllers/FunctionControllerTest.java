package Labs_OOP_sem_3.controllers;

import Labs_OOP_sem_3.App.Application;
import Labs_OOP_sem_3.dto.FunctionDto;
import Labs_OOP_sem_3.dto.PointDto;
import Labs_OOP_sem_3.entities.FunctionEntity;
import Labs_OOP_sem_3.repositories.FunctionRepository;
import Labs_OOP_sem_3.repositories.PointRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static Labs_OOP_sem_3.convertos.ConvertorToFuncEntity.convert;
import static Labs_OOP_sem_3.convertos.ConvertorToPointEntity.convertToEntity;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@Transactional
public class FunctionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    FunctionDto func;
    FunctionEntity entFunc;
    @Autowired
    FunctionRepository funcRepository;
    @Autowired
    PointRepository pointRepository;

    @BeforeEach
    void setUp() {
        funcRepository.restartSeq();
        pointRepository.restartSeq();
        func = FunctionDto.builder().id(1).points(new ArrayList<>()).name("Test").build();
        entFunc = FunctionEntity.builder().id(1).name("Test").build();
    }

    @Test
    @WithMockUser
    void postTest() throws Exception {
        entFunc = FunctionEntity.builder().id(1).name("0").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/functions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().
                writeValueAsString(func))).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(entFunc)));

    }

    @Test
    @WithMockUser
    void getTestByNameTest() throws Exception {
        entFunc = FunctionEntity.builder().id(1).name("0").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/functions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().
                writeValueAsString(func)));
        mockMvc.perform(MockMvcRequestBuilders.get("/functions/name?name=0").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(entFunc)));
    }

    @Test
    @WithMockUser
    void getTestByIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/functions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().
                writeValueAsString(func)));
        PointDto p1 = PointDto.builder().function(convert(func)).id(1).x(1.0).y(2.0).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/points").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(p1)));
        PointDto p2 = PointDto.builder().function(convert(func)).id(2).x(3.0).y(5.0).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/points").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(p2)));
        FunctionDto func1 = FunctionDto.builder().id(1).name("Test").points(new ArrayList<>()).build();
        func1.getPoints().add(convertToEntity(p1));
        func1.getPoints().add(convertToEntity(p2));
        mockMvc.perform(MockMvcRequestBuilders.get("/functions/funcId?funcId=1").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(func1)));

    }

    @Test
    @WithMockUser
    void deleteTest() throws Exception {
        entFunc = FunctionEntity.builder().id(1).name("0").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/functions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().
                writeValueAsString(func)));
        mockMvc.perform(MockMvcRequestBuilders.delete("/functions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().
                writeValueAsString(func))).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(entFunc)));
    }

    @Test
    @WithMockUser
    void updateTest() throws Exception {
        var updFunc = FunctionDto.builder().id(1).points(new ArrayList<>()).name("Test2").build();
        var updFuncEnt = FunctionEntity.builder().id(1).name("Test2").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/functions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().
                writeValueAsString(func)));
        mockMvc.perform(MockMvcRequestBuilders.put("/functions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().
                writeValueAsString(updFunc)));
        mockMvc.perform(MockMvcRequestBuilders.get("/functions/name?name=Test2").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(updFuncEnt)));


    }

    @AfterEach
    @WithMockUser
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/functions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().
                writeValueAsString(func)));
    }
}
