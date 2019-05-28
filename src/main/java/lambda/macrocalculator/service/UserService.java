package lambda.macrocalculator.service;

import lambda.macrocalculator.model.User;

import java.security.Principal;
import java.util.List;

public interface UserService
{
	List<User> findAll();

	User findUserById(long id);

	User findUserByUsername(String username);

	void delete(long id);

	User save(User user);

	User getCurrentUser(Principal principal);
}
