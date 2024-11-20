package Labs_OOP_sem_3.repositories;

import Labs_OOP_sem_3.entities.FunctionEntity;
import Labs_OOP_sem_3.entities.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PointRepository extends JpaRepository<PointEntity, Integer>
{
    @Query("SELECT p FROM PointEntity p WHERE p.function.id = :functionId")
    ArrayList<PointEntity> findByFunction(@Param("functionId") Integer functionId);
}
