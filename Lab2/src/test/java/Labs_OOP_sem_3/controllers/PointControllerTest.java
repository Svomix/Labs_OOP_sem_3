package Labs_OOP_sem_3.controllers;

import Labs_OOP_sem_3.App.Application;
import Labs_OOP_sem_3.dto.PointDto;
import Labs_OOP_sem_3.entities.FunctionEntity;
import Labs_OOP_sem_3.entities.PointEntity;
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

import static Labs_OOP_sem_3.convertos.ConvertorToPointEntity.convertToEntity;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@Transactional
public class PointControllerTest {
    @Autowired
    private MockMvc mockMvc;
    FunctionEntity func = FunctionEntity.builder().id(1).name("asd").build();
    PointDto p;
    PointEntity p1;
    @Autowired
    private FunctionRepository functionRepository;
    @Autowired
    private PointRepository pointRepository;
    @BeforeEach
    void setUp() {
        functionRepository.restartSeq();
        pointRepository.restartSeq();
        p = PointDto.builder().id(1).x(1.0).y(2.0).function(func).build();
        p1 = PointEntity.builder().id(1).xValue(1.0).yValue(2.0).function(func).build();
    }

    @Test
    @WithMockUser
    void postPoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/points").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(p))).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(p1)));
    }

    @Test
    @WithMockUser
    void read() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/points").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(p))).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/points/id?id=1").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(p1)));
    }

    @Test
    @WithMockUser
    void update() throws Exception {
        var p2 = PointDto.builder().id(1).x(4.0).y(5.0).function(func).build();
        var p3 = PointEntity.builder().id(1).xValue(4.0).yValue(5.0).function(func).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/points").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(p))).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.put("/points/update").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(p2))).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(p3)));
    }
    @Test
    @WithMockUser
    void findByFunc() throws Exception
    {
        var arrP = new ArrayList<PointEntity>();
        var p2 = PointDto.builder().id(2).x(4.0).y(5.0).function(func).build();
        arrP.add(p1);
        arrP.add(convertToEntity(p2));
        mockMvc.perform(MockMvcRequestBuilders.post("/points").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(p))).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/points").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(p2))).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/points/find?funcId=1").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(arrP)));
    }
    @AfterEach
    void tearDown() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/points").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(p))).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(p1)));
    }
}
