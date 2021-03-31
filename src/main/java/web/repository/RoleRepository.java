package web.repository;
/**
 * @author Eugene Kashitsyn
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
}
