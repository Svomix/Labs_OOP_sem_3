package repositories;


import entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>
{
    User findById(int id);
    @Query("SELECT id from users where user_name = :userName")
    List<User> findByUsername(String userName);
    List<User> findByUsernameContaining(String username);
    List<User> findByUsernameContainingIgnoreCase(String username);

}
