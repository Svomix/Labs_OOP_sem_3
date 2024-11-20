package Labs_OOP_sem_3.repositories;

import Labs_OOP_sem_3.entities.UserEntity;
import Labs_OOP_sem_3.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, Integer>
{
    Optional<UserEntity> findByUsername(String username);
}
