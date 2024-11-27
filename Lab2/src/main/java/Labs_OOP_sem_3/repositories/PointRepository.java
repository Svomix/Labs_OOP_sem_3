package Labs_OOP_sem_3.repositories;

import Labs_OOP_sem_3.entities.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PointRepository extends JpaRepository<PointEntity, Integer> {
    @Query(value = "SELECT * from points where function_id = :functionId;", nativeQuery = true)
    ArrayList<PointEntity> findByFunction(@Param("functionId") Integer functionId);

    @Modifying
    @Query(value = "ALTER SEQUENCE points_id_seq RESTART WITH 1;", nativeQuery = true)
    void restartSeq();
}
