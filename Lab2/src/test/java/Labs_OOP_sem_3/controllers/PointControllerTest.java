package Labs_OOP_sem_3.controllers;

import Labs_OOP_sem_3.App.Application;
import Labs_OOP_sem_3.convertos.ConvertorToFuncEntity;
import Labs_OOP_sem_3.convertos.ConvertorToPointDto;
import Labs_OOP_sem_3.dto.FunctionDto;
import Labs_OOP_sem_3.dto.PointDto;
import Labs_OOP_sem_3.entities.FunctionEntity;
import Labs_OOP_sem_3.entities.PointEntity;
import Labs_OOP_sem_3.repositories.FunctionRepository;
import Labs_OOP_sem_3.repositories.PointRepository;
import Labs_OOP_sem_3.utlis.HashUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    FunctionDto functionDto;
    FunctionEntity func = FunctionEntity.builder().id(1).name("-1310701078").build();
    PointDto p;
    PointEntity p1;
    @Autowired
    private FunctionRepository functionRepository;
    @Autowired
    private PointRepository pointRepository;
    ArrayList<PointEntity> points = new ArrayList<>();

    @BeforeEach
    void setUp() throws Exception {
        functionRepository.restartSeq();
        pointRepository.restartSeq();
        functionDto = FunctionDto.builder().id(1).points(new ArrayList<>()).name("0").build();
        p = PointDto.builder().id(1).x(1.0).y(1.0).function(ConvertorToFuncEntity.convert(functionDto)).build();
        p1 = PointEntity.builder().id(1).xValue(1.0).yValue(1.0).function(func).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/functions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().
                writeValueAsString(functionDto)));
    }

    @Test
    @WithMockUser
    void postPoint() throws Exception {
        points.add(convertToEntity(p));
        p1.getFunction().setName(HashUtil.hash(points)+"");
        mockMvc.perform(MockMvcRequestBuilders.post("/points").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(p))).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(p1)));
        points.getFirst().getFunction().setName(HashUtil.hash(points)+"");
    }

    /*
    @Test
    @WithMockUser
    void read() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/points").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(p))).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/points/id?id=1").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(p1)));
    }

     */

    @Test
    @WithMockUser
    void update() throws Exception {
        points.add(convertToEntity(p));
        var p2 = PointDto.builder().id(1).x(1.0).y(5.0).function(func).build();
        p2.getFunction().setName(HashUtil.hash(points)+"");
        var p3 = PointEntity.builder().id(2).xValue(1.0).yValue(5.0).function(FunctionEntity.builder().id(1).name(HashUtil.hash(points)+"").build()).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/points").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(p))).andExpect(MockMvcResultMatchers.status().isOk());
        points.removeFirst();
        points.add(convertToEntity(p2));
        p3.getFunction().setName(HashUtil.hash(points)+"");
        mockMvc.perform(MockMvcRequestBuilders.put("/points/update").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(p2))).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(p3)));
        p2.getFunction().setName(HashUtil.hash(points)+"");
    }

    @Test
    @WithMockUser
    void findByFunc() throws Exception {
        points.add(convertToEntity(p));
        mockMvc.perform(MockMvcRequestBuilders.post("/points").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(p))).andExpect(MockMvcResultMatchers.status().isOk());
        points.getFirst().getFunction().setName(""+HashUtil.hash(points));
//        var p2 = PointDto.builder().id(2).x(4.0).y(5.0).function(FunctionEntity.builder().id(p.getId()).name(HashUtil.hash(points)+"").build()).build();
//        mockMvc.perform(MockMvcRequestBuilders.post("/points").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(p2))).andExpect(MockMvcResultMatchers.status().isOk());
//        points.add(convertToEntity(p2));
//        points.getFirst().getFunction().setName(""+HashUtil.hash(points));
//        points.get(1).getFunction().setName(""+HashUtil.hash(points));
        mockMvc.perform(MockMvcRequestBuilders.get("/points/find?name="+ HashUtil.hash(points)).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(points)));
    }

    @AfterEach
    void tearDown() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/points").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(ConvertorToPointDto.convertToDto(points.removeFirst()))));
        func = FunctionEntity.builder().id(1).name(HashUtil.hash(points)+"").build();
        mockMvc.perform(MockMvcRequestBuilders.delete("/functions").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().
                writeValueAsString(func)));
    }
}
