package lambda.macrocalculator.repos;

import lambda.macrocalculator.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends CrudRepository<User, Long>
{
	User findByUsername(String name);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM userroles WHERE userid = :userid", nativeQuery = true)
	void deleteUserRole(long userid);
}