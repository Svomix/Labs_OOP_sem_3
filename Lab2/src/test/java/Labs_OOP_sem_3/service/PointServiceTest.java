package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.App.Application;
import Labs_OOP_sem_3.dto.FunctionDto;
import Labs_OOP_sem_3.dto.PointDto;
import Labs_OOP_sem_3.entities.FunctionEntity;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static Labs_OOP_sem_3.convertos.ConvertorToFuncEntity.convert;

@SpringBootTest(classes = Application.class)
@Transactional
public class PointServiceTest {
    @Autowired
    private PointService pointService;
    @Autowired
    private FunctionService functionService;
    private PointDto point;
    private FunctionDto function;
    PointDto point1;
    PointDto point2;
    PointDto point3;

    @BeforeEach
    public void createPoint() {
        functionService.updateSequence();
        pointService.updateSequence();
        function = FunctionDto.builder().id(1).name("12").build();
        point = PointDto.builder().id(1).function(convert(function)).x(2.0).y(3.0).build();
        pointService.create(point);
    }

    @Test
    void create() {
        Assertions.assertEquals(2, pointService.read(point.getId()).getXValue());
        Assertions.assertEquals(3, pointService.read(point.getId()).getYValue());
    }

    @Test
    void read() {
        Assertions.assertEquals(2, pointService.read(point.getId()).getXValue());
        Assertions.assertEquals(3, pointService.read(point.getId()).getYValue());
        Assertions.assertNull(pointService.read(point.getId() + 1));
    }

    @Test
    void update() {
        var updPoint = PointDto.builder().id(1).function(FunctionEntity.builder().name("456").build()).x(5).y(6).build();
        pointService.update(updPoint);
        Assertions.assertEquals(5, pointService.read(point.getId()).getXValue());
        Assertions.assertEquals(6, pointService.read(point.getId()).getYValue());
        Assertions.assertEquals(updPoint.getFunction().getName(), pointService.read(point.getId()).getFunction().getName());
    }

    @Test
    void delete() {
        var updPoint = PointDto.builder().id(2).function(FunctionEntity.builder().name("456").build()).build();
        pointService.create(updPoint);
        pointService.delete(updPoint);
        Assertions.assertNull(pointService.read(updPoint.getId()));
    }

    @Test
    void findByFunc() {
        ArrayList arr = new ArrayList();
        point1 = PointDto.builder().id(2).function(convert(function)).x(3.0).y(5.0).build();
        point2 = PointDto.builder().id(3).function(convert(function)).x(1.0).y(3.0).build();
        point3 = PointDto.builder().id(4).function(convert(function)).x(2.0).y(8.0).build();
        pointService.create(point1);
        pointService.create(point2);
        pointService.create(point3);
        arr.add(point);
        arr.add(point1);
        arr.add(point2);
        arr.add(point3);
        System.out.println(pointService.findByFunc(function.getId()));
        Assertions.assertEquals(arr.size(), pointService.findByFunc(function.getId()).size());
        Assertions.assertNull(pointService.findByFunc(function.getId() + 1));
        pointService.delete(point1);
        pointService.delete(point2);
        pointService.delete(point3);
    }

    @AfterEach
    void destroy() {
        pointService.delete(point);
        functionService.delete(function);
    }
}
