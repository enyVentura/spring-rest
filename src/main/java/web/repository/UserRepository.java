package web.repository;
/**
 * @author Eugene Kashitsyn
 */

import org.springframework.stereotype.Repository;
import web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
