package lambda.macrocalculator.service;

import lambda.macrocalculator.exception.ResourceNotFoundException;
import lambda.macrocalculator.model.Macros;
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
	public User findUserByUsername(String username) throws ResourceNotFoundException
	{
		User user = userRepos.findByUsername(username);
		if(user == null)
		{
			throw new ResourceNotFoundException("Could not find user with name " + username);
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
	public User findUserById(long id) throws ResourceNotFoundException
	{
		return userRepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find user with id " + id));
	}

	@Transactional
	@Override
	public void delete(long id) throws ResourceNotFoundException
	{
		if(userRepos.findById(id).isPresent())
		{
			userRepos.deleteUserRole(id);
			userRepos.deleteById(id);
		} else
		{
			throw new ResourceNotFoundException("Could not find user with id " + id);
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
		newUser.setGender(user.getGender());

		ArrayList<UserRoles> newRoles = new ArrayList<>();

		Role userRole = roleRepos.findByName("USER");

		if(userRole == null)
		{
			roleRepos.save(new Role("USER"));
			Role role = roleRepos.findByName("USER");
			newRoles.add(new UserRoles(newUser, role));
		} else {
			newRoles.add(new UserRoles(newUser, userRole));
		}

		newUser.setUserRoles(newRoles);

		return userRepos.save(newUser);
	}

	@Transactional
	@Override
	public User update(User user, Principal principal)
	{
		User currentUser = getCurrentUser(principal);

		if(user.getCurrentweight() != currentUser.getCurrentweight())
		{
			currentUser.setCurrentweight(user.getCurrentweight());
		}

		if(user.getGoal() != null)
		{
			currentUser.setGoal(user.getGoal());
		}

		return userRepos.save(currentUser);
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