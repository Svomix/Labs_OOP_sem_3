package Labs_OOP_sem_3.repositories;

import Labs_OOP_sem_3.entities.CompFuncEntity;
import Labs_OOP_sem_3.entities.FunctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
@Transactional
public interface CompRepository extends JpaRepository<CompFuncEntity, Integer> {
    @Query(value = "SELECT f FROM functions f WHERE f.id_comp = :id_comp",nativeQuery = true)
    ArrayList<FunctionEntity> findAllFuncById(int id_comp);
    @Query(value = "SELECT name FROM comp_func WHERE id_user = :id_user", nativeQuery = true)
    ArrayList<String> findByIdUser(@Param("id_user") int id_user);
    @Query(value = "SELECT id FROM comp_func WHERE name = :name", nativeQuery = true)
    CompFuncEntity findByName(@Param("name") String name);
}