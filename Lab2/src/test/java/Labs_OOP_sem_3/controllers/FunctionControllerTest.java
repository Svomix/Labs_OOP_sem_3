package Labs_OOP_sem_3.controllers;

import Labs_OOP_sem_3.App.Application;
import Labs_OOP_sem_3.dto.FunctionDtoList;
import Labs_OOP_sem_3.entities.FunctionEntity;
import Labs_OOP_sem_3.entities.PointEntity;
import Labs_OOP_sem_3.repositories.FunctionRepository;
import Labs_OOP_sem_3.repositories.PointRepository;
import Labs_OOP_sem_3.utlis.HashUtil;
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

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@Transactional
public class FunctionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    FunctionDtoList func;
    FunctionEntity entFunc;
    @Autowired
    FunctionRepository funcRepository;
    @Autowired
    PointRepository pointRepository;

    @BeforeEach
    void setUp() {
        funcRepository.restartSeq();
        pointRepository.restartSeq();
        func = FunctionDtoList.builder().id(1).points(new ArrayList<>()).hash("0").build();
    }

    @Test
    @WithMockUser
    void postTest() throws Exception {
        entFunc = FunctionEntity.builder().id(1).hash("0").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/functions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().
                writeValueAsString(func))).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(entFunc)));

    }

    @Test
    @WithMockUser
    void getTestByNameTest() throws Exception {
        entFunc = FunctionEntity.builder().id(1).hash("0").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/functions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().
                writeValueAsString(func)));
        mockMvc.perform(MockMvcRequestBuilders.get("/functions/name?name=0").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(entFunc)));
    }

    @Test
    @WithMockUser
    void updateTest() throws Exception {
        var updFunc = FunctionDtoList.builder().id(1).points(new ArrayList<>()).hash("0").build();
        PointEntity point = PointEntity.builder().xValue(1).yValue(1).function(convert(func)).build();
        updFunc.getPoints().add(point);
        var updFuncEnt = FunctionEntity.builder().id(1).hash("-1310701078").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/functions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().
                writeValueAsString(func)));
        mockMvc.perform(MockMvcRequestBuilders.put("/functions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().
                writeValueAsString(updFunc)));
        mockMvc.perform(MockMvcRequestBuilders.get("/functions/name?name="+ HashUtil.hash(updFunc.getPoints())).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(updFuncEnt)));


    }

    @AfterEach
    @WithMockUser
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/functions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().
                writeValueAsString(func)));
    }
}
