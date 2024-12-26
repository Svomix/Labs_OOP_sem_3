package Labs_OOP_sem_3.repositories;

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
public interface FunctionRepository extends JpaRepository<FunctionEntity, Integer> {
    FunctionEntity findByHash(String functionHash);

    FunctionEntity findByName(String name);

    @Query(value = "SELECT id, function_hash, function_type, id_user, composite, name, id_comp FROM functions WHERE id_user = :id_user", nativeQuery = true)
    ArrayList<FunctionEntity> findByIdUser(@Param("id_user") int id_user);

    @Query(value = "SELECT id, function_hash, function_type, id_user, composite, name, id_comp FROM functions WHERE id_COMP = :id", nativeQuery = true)
    ArrayList<FunctionEntity> findByIdComp(@Param("id") int id);

    @Modifying
    @Query(value = "ALTER SEQUENCE functions_id_seq RESTART WITH 1;", nativeQuery = true)
    void restartSeq();
}
