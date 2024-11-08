package dao;

import entities.FunctionEntity;
import entities.PointEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class MainDaoTest {
    private static final FunctionDaoImpl functionDaoImpl = new FunctionDaoImpl();
    private static final PointDao pointDao = new PointDao();

    @BeforeAll
    static void tearDown() {
        FunctionEntity functionEntity = FunctionEntity.builder()
                .functionType("linear")
                .points(new ArrayList<>(){})
                .build();
        functionDaoImpl.create(functionEntity);
        PointEntity pointEntity = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(1.0)
                .yValue(1.0)
                .build();
        pointDao.create(pointEntity);
        PointEntity pointEntity1 = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(2.0)
                .yValue(2.0)
                .build();
        pointDao.create(pointEntity1);
        PointEntity pointEntity3 = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(3.0)
                .yValue(3.0)
                .build();
        pointDao.create(pointEntity3);
        PointEntity pointEntity4 = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(4.0)
                .yValue(4.0)
                .build();
        pointDao.create(pointEntity4);
        pointEntity4.setYValue(6.0);
        pointDao.update(pointEntity4);
        PointEntity pointEntity5 = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(5.0)
                .yValue(5.0)
                .build();
        pointDao.create(pointEntity5);
        List<PointEntity> pointEntities = new ArrayList<>();
        pointEntities.add(pointEntity);
        pointEntities.add(pointEntity1);
        pointEntities.add(pointEntity3);
        pointEntities.add(pointEntity4);
        pointEntities.add(pointEntity5);
        functionEntity.setPoints(pointEntities);
        functionDaoImpl.update(functionEntity);
    }

    @Test
    void createTest() {
        FunctionEntity functionEntity = FunctionEntity.builder()
                .functionType("tip")
                .points(new ArrayList<>(){})
                .build();
        functionDaoImpl.create(functionEntity);
        PointEntity pointEntity = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(1.0)
                .yValue(1.0)
                .build();
        pointDao.create(pointEntity);
        PointEntity pointRead = pointDao.read("""
                select * from labs.public.points
                where function_type = 'tip' and yValue = 1.0
                """);
        pointRead.setYValue(50.0);
        pointDao.update(pointRead);
        PointEntity pointEntity1 = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(2.0)
                .yValue(2.0)
                .build();
        pointDao.create(pointEntity1);
        PointEntity pointEntity3 = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(3.0)
                .yValue(3.0)
                .build();
        pointDao.create(pointEntity3);
        PointEntity pointEntity4 = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(4.0)
                .yValue(4.0)
                .build();
        pointDao.create(pointEntity4);
        PointEntity pointEntity5 = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(5.0)
                .yValue(5.0)
                .build();
        pointDao.create(pointEntity5);
        List<PointEntity> pointEntities = new ArrayList<>();
        pointEntities.add(pointEntity);
        pointEntities.add(pointEntity1);
        pointEntities.add(pointEntity3);
        pointEntities.add(pointEntity4);
        pointEntities.add(pointEntity5);
        functionEntity.setPoints(pointEntities);
        functionDaoImpl.update(functionEntity);
    }


    @AfterAll
    static void tearDown2() {
        PointEntity pointRead = pointDao.read("""
                select * from labs.public.points
                where function_type = 'tip' and yValue = 50.0
                """);
        pointDao.delete(pointRead);
        FunctionEntity functionEntity = functionDaoImpl.read("""
                select * from labs.public.functions
                where function_type = 'linear'
                """);
        FunctionEntity functionEntity2 = functionDaoImpl.read("""
                select * from labs.public.functions
                where function_type = 'tip'
                """);
        functionDaoImpl.delete(functionEntity2);
        functionDaoImpl.delete(functionEntity);
    }

}