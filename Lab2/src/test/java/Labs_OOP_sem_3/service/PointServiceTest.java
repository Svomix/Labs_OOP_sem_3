package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.App.Application;
import Labs_OOP_sem_3.convertos.ConvertorToPointDto;
import Labs_OOP_sem_3.convertos.ConvertorToPointEntity;
import Labs_OOP_sem_3.dto.FunctionDtoList;
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
    private FunctionDtoList function;
    ArrayList<PointEntity> points = new ArrayList<>();

    @BeforeEach
    public void createPoint() {
        functionService.updateSequence();
        pointService.updateSequence();
        function = FunctionDtoList.builder().id(1).hash("0").build();
        functionService.create(function);
        point = PointDto.builder().id(1).function(convert(function)).x(2.0).y(3.0).build();
        pointService.create(point);
        points.add(ConvertorToPointEntity.convertToEntity(point));
        function.setPoints(points);
        function.setHash("" + HashUtil.hash(points));
        points.getFirst().getFunction().setHash(function.getHash());
    }

    @Test
    void create() {
        Assertions.assertEquals(2, pointService.readByFuncIdAndPoint(point.getId(), point.getX()).getXValue());
        Assertions.assertEquals(3, pointService.readByFuncIdAndPoint(point.getId(), point.getX()).getYValue());
    }

    @Test
    void update() {
        var updPoint = PointDto.builder().id(1).function(FunctionEntity.builder().hash("" + HashUtil.hash(points)).build()).x(2.0).y(6.0).build();
        pointService.update(updPoint);
        points.removeFirst();
        points.add(ConvertorToPointEntity.convertToEntity(updPoint));
        Assertions.assertEquals(2, pointService.readByFuncIdAndPoint(point.getId(), point.getX()).getXValue());
        Assertions.assertEquals(6, pointService.readByFuncIdAndPoint(point.getId(), point.getX()).getYValue());
        updPoint.getFunction().setHash(HashUtil.hash(points) + "");
        Assertions.assertEquals(updPoint.getFunction().getHash(), pointService.readByFuncIdAndPoint(point.getId(), point.getX()).getFunction().getHash());
    }

    @AfterEach
    void destroy() {
        pointService.delete(ConvertorToPointDto.convertToDto(points.removeFirst()));
        function.setHash(HashUtil.hash(points) + "");
        functionService.delete(function);
    }
}
