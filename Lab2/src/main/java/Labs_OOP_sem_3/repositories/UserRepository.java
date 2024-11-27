package Labs_OOP_sem_3.repositories;

import Labs_OOP_sem_3.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);

    @Modifying
    @Query(value = "ALTER SEQUENCE users_id_seq RESTART WITH 1;", nativeQuery = true)
    void restartSeq();
}
