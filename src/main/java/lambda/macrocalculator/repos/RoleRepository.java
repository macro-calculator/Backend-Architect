package lambda.macrocalculator.repos;

import lambda.macrocalculator.model.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RoleRepository extends CrudRepository<Role, Long>
{
	@Transactional
	@Modifying
	@Query(value = "DELETE from UserRoles where userid = :userid")
	void deleteUserRolesByUserId(long userid);

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO UserRoles(userid, roleid) values (:userid, :roleid)", nativeQuery = true)
	void insertUserRoles(long userid, long roleid);

	Role findByName(String rolename);
}