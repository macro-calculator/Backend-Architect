package lambda.macrocalculator.service;

import lambda.macrocalculator.model.Role;
import lambda.macrocalculator.model.User;
import lambda.macrocalculator.model.UserRoles;
import lambda.macrocalculator.repos.RoleRepository;
import lambda.macrocalculator.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService
{

	@Autowired
	private UserRepository userRepos;
	@Autowired
	private RoleRepository roleRepos;

	@Override
	public User findUserByUsername(String username) throws UsernameNotFoundException
	{
		User user = userRepos.findByUsername(username);
		if(user == null)
		{
			throw new UsernameNotFoundException("Invalid username or password");
		}
		return user;
	}

	@Override
	public List<User> findAll()
	{
		ArrayList<User> users = new ArrayList<>();
		userRepos.findAll().iterator().forEachRemaining(users::add);
		return users;
	}

	@Override
	public User findUserById(long id) throws EntityNotFoundException
	{
		return userRepos.findById(id).orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
	}

	@Override
	public void delete(long id)
	{
		if(userRepos.findById(id).isPresent())
		{
			userRepos.deleteUserRole(id);
			userRepos.deleteById(id);
		} else
		{
			throw new EntityNotFoundException(Long.toString(id));
		}
	}

	@Transactional
	@Override
	public User save(User user)
	{
		User newUser = new User();

		newUser.setUsername(user.getUsername());
		newUser.setPlainTextPass(user.getPassword());
		newUser.setEmail(user.getEmail());
		newUser.setAge(user.getAge());
		newUser.setHeight(user.getHeight());
		newUser.setCurrentweight(user.getCurrentweight());
		newUser.setName(user.getName());
		newUser.setActivitylevel(user.getActivitylevel());
		newUser.setGoal(user.getGoal());

		ArrayList<UserRoles> newRoles = new ArrayList<>();

		//Todo make conditional. No need to create a new USER role for each new user
//		Assigns a basic user role as a default
		Role baseRole = new Role("USER");
		roleRepos.save(baseRole);

		newRoles.add(new UserRoles(newUser, baseRole));

		newUser.setUserRoles(newRoles);


		return userRepos.save(newUser);
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User user = userRepos.findByUsername(username);

		if (user == null)
		{
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthority());
	}

	public User getCurrentUser(Principal principal)
	{
		User currentUser = findUserByUsername(principal.getName());
		return currentUser;
	}
}