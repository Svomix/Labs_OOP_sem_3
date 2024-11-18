package Labs_OOP_sem_3.repositories;

import Labs_OOP_sem_3.entities.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<PointEntity, Integer>
{
}
