package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.entities.FunctionEntity;
import Labs_OOP_sem_3.entities.PointEntity;
import Labs_OOP_sem_3.service.FunctionServiceImpl;
import Labs_OOP_sem_3.service.PointService;
import Labs_OOP_sem_3.service.Service;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class FunctionServiceImplTest {
    private static final Service<FunctionEntity> functionService = new FunctionServiceImpl();
    private static final Service<PointEntity> pointService = new PointService();

    @BeforeAll
    static void tearDown() {
        FunctionEntity functionEntity = FunctionEntity.builder()
                .name("linea")
                .points(new ArrayList<>() {
                })
                .build();
        functionService.create(functionEntity);
        PointEntity pointEntity = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(1.0)
                .yValue(1.0)
                .build();
        pointService.create(pointEntity);
        PointEntity pointEntity1 = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(2.0)
                .yValue(2.0)
                .build();
        pointService.create(pointEntity1);
        PointEntity pointEntity3 = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(3.0)
                .yValue(3.0)
                .build();
        pointService.create(pointEntity3);
        PointEntity pointEntity4 = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(4.0)
                .yValue(4.0)
                .build();
        pointService.create(pointEntity4);
        PointEntity pointEntity5 = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(5.0)
                .yValue(5.0)
                .build();
        pointService.create(pointEntity5);
        List<PointEntity> pointEntities = new ArrayList<>();
        pointEntities.add(pointEntity);
        pointEntities.add(pointEntity1);
        pointEntities.add(pointEntity3);
        pointEntities.add(pointEntity4);
        pointEntities.add(pointEntity5);
        functionEntity.setPoints(pointEntities);
        functionService.update(functionEntity);
    }

    @Test
    void create() {
        FunctionEntity functionEntity = FunctionEntity.builder()
                .name("tips")
                .points(new ArrayList<>() {
                })
                .build();
        functionService.create(functionEntity);
        PointEntity pointEntity = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(1.0)
                .yValue(1.0)
                .build();
        pointService.create(pointEntity);
        PointEntity pointRead = pointService.read("""
                select p.id, p.function_id, p.y, p.x from labs.public.points p,labs.public.functions f
                where p.function_id = (select t.id from labs.public.functions t where t.function_type = 'linea') and p.y = 1.0
                """);
        pointRead.setYValue(50.0);
        pointService.update(pointRead);
        PointEntity pointEntity1 = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(2.0)
                .yValue(2.0)
                .build();
        pointService.create(pointEntity1);
        PointEntity pointEntity3 = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(3.0)
                .yValue(3.0)
                .build();
        pointService.create(pointEntity3);
        PointEntity pointEntity4 = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(4.0)
                .yValue(4.0)
                .build();
        pointService.create(pointEntity4);
        PointEntity pointEntity5 = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(5.0)
                .yValue(5.0)
                .build();
        pointService.create(pointEntity5);
        List<PointEntity> pointEntities = new ArrayList<>();
        pointEntities.add(pointEntity);
        pointEntities.add(pointEntity1);
        pointEntities.add(pointEntity3);
        pointEntities.add(pointEntity4);
        pointEntities.add(pointEntity5);
        functionEntity.setPoints(pointEntities);
        functionService.update(functionEntity);
    }


    @AfterAll
    static void tearDown2() {
        PointEntity pointRead = pointService.read("""
                select p.id, p.function_id, p.y, p.x from labs.public.points p,labs.public.functions f
                where p.function_id = (select t.id from labs.public.functions t where t.function_type = 'linea') and p.y = 50.0
                """);
        pointService.delete(pointRead);
        FunctionEntity functionEntity = functionService.read("""
                select * from labs.public.functions
                where function_type = 'linea'
                """);
        FunctionEntity functionEntity2 = functionService.read("""
                select * from labs.public.functions
                where function_type = 'tips'
                """);
        functionService.delete(functionEntity2);
        functionService.delete(functionEntity);
    }
}