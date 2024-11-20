package Labs_OOP_sem_3.repositories;

import Labs_OOP_sem_3.entities.FunctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FunctionRepository extends JpaRepository<FunctionEntity, Integer>
{
     FunctionEntity findByName(String functionType);
}
