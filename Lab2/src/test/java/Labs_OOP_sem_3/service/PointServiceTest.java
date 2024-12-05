package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.App.Application;
import Labs_OOP_sem_3.convertos.ConvertorToPointDto;
import Labs_OOP_sem_3.convertos.ConvertorToPointEntity;
import Labs_OOP_sem_3.dto.FunctionDto;
import Labs_OOP_sem_3.dto.PointDto;
import Labs_OOP_sem_3.entities.FunctionEntity;
import Labs_OOP_sem_3.entities.PointEntity;
import Labs_OOP_sem_3.utlis.HashUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    ArrayList<PointEntity> points = new ArrayList<>();

    @BeforeEach
    public void createPoint() {
        functionService.updateSequence();
        pointService.updateSequence();
        function = FunctionDto.builder().id(1).name("0").build();
        functionService.create(function);
        point = PointDto.builder().id(1).function(convert(function)).x(2.0).y(3.0).build();
        pointService.create(point);
        points.add(ConvertorToPointEntity.convertToEntity(point));
        function.setPoints(points);
        function.setName("" + HashUtil.hash(points));
        points.getFirst().getFunction().setName(function.getName());
    }

    @Test
    void create() {
        Assertions.assertEquals(2, pointService.readByFuncIdAndPoint(point.getId(), point.getX()).getXValue());
        Assertions.assertEquals(3, pointService.readByFuncIdAndPoint(point.getId(), point.getX()).getYValue());
    }

    @Test
    void update() {
        var updPoint = PointDto.builder().id(1).function(FunctionEntity.builder().name("" + HashUtil.hash(points)).build()).x(2.0).y(6.0).build();
        pointService.update(updPoint);
        points.removeFirst();
        points.add(ConvertorToPointEntity.convertToEntity(updPoint));
        Assertions.assertEquals(2, pointService.readByFuncIdAndPoint(point.getId(), point.getX()).getXValue());
        Assertions.assertEquals(6, pointService.readByFuncIdAndPoint(point.getId(), point.getX()).getYValue());
        updPoint.getFunction().setName(HashUtil.hash(points) + "");
        Assertions.assertEquals(updPoint.getFunction().getName(), pointService.readByFuncIdAndPoint(point.getId(), point.getX()).getFunction().getName());
    }

    @AfterEach
    void destroy() {
        pointService.delete(ConvertorToPointDto.convertToDto(points.removeFirst()));
        function.setName(HashUtil.hash(points) + "");
        functionService.delete(function);
    }
}
