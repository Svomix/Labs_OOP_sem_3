//package Labs_OOP_sem_3.service;
//
//import Labs_OOP_sem_3.App.Application;
//import Labs_OOP_sem_3.dto.FunctionDto;
//import Labs_OOP_sem_3.dto.PointDto;
//import Labs_OOP_sem_3.entities.FunctionEntity;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//
//import static Labs_OOP_sem_3.convertos.ConvertorToFuncEntity.convert;
//
//@SpringBootTest(classes = Application.class)
//public class PointServiceTest {
//    @Autowired
//    private PointService pointService;
//    @Autowired
//    private FunctionService functionService;
//    private PointDto point;
//    private FunctionDto function;
//
//    @BeforeEach
//    public void createPoint()
//    {
//        function = FunctionDto.builder().name("12").points(new ArrayList<>()).build();
//        point = PointDto.builder().id(1).function(convert(function)).x(2.0).y(3.0).build();
//        pointService.create(point);
//    }
//    @Test
//    void create() {
//        Assertions.assertEquals(2, pointService.read(point.getId()).getXValue());
//        Assertions.assertEquals(3, pointService.read(point.getId()).getYValue());
//    }
//
//    @Test
//    void read() {
//        Assertions.assertEquals(2, pointService.read(3).getXValue());
//        Assertions.assertEquals(3, pointService.read(3).getYValue());
//    }
//
//    @Test
//    void update() {
//        var updPoint = PointDto.builder().id(1).function(FunctionEntity.builder().name("456").points(new ArrayList<>()).build()).x(5).y(3.0).build();
//        pointService.update(updPoint);
//        Assertions.assertEquals(5, pointService.read(1).getXValue());
//    }
//
//    @Test
//    void delete() {
//        var updPoint = PointDto.builder().id(2).function(FunctionEntity.builder().name("456").points(new ArrayList<>()).build()).x(5).y(3.0).build();
//        pointService.create(updPoint);
//        pointService.delete(updPoint);
//        Assertions.assertNull(pointService.read(updPoint.getId()));
//    }
//
//    @Test
//    void findByFunc() {
//    }
//
//    @AfterEach
//    @Transactional
//    void destroy() {
//        pointService.updateSequence();
//       functionService.delete(function);
//    }
//}
