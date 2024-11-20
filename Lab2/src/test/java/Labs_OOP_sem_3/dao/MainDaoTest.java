package Labs_OOP_sem_3.dao;

import Labs_OOP_sem_3.dao.FunctionDao;
import Labs_OOP_sem_3.dao.PointDao;
import Labs_OOP_sem_3.entities.FunctionEntity;
import Labs_OOP_sem_3.entities.PointEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class MainDaoTest {
    private static final FunctionDao functionDaoImpl = new FunctionDao();
    private static final PointDao pointDao = new PointDao();

    @BeforeAll
    static void tearDown() {
        FunctionEntity functionEntity = FunctionEntity.builder()
                .name("linear")
                .points(new ArrayList<>() {
                })
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
                .name("tip")
                .points(new ArrayList<>() {
                })
                .build();
        functionDaoImpl.create(functionEntity);
        PointEntity pointEntity = PointEntity.builder()
                .functionEntity(functionEntity)
                .xValue(1.0)
                .yValue(1.0)
                .build();
        pointDao.create(pointEntity);
        PointEntity pointRead = pointDao.read("""
                select p.id, p.function_id, p.y, p.x from labs.public.points p,labs.public.functions f
                where p.function_id = (select t.id from labs.public.functions t where t.function_type = 'linear') and p.y = 1.0
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
                select p.id, p.function_id, p.y, p.x from labs.public.points p,labs.public.functions f
                where p.function_id = (select t.id from labs.public.functions t where t.function_type = 'linear') and p.y = 50.0
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