package lambda.macrocalculator.service;

import lambda.macrocalculator.exception.ResourceNotFoundException;
import lambda.macrocalculator.model.Macros;
import lambda.macrocalculator.model.User;
import lambda.macrocalculator.repos.MacrosRepository;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@Transactional
public class MacrosServiceImpl implements MacrosService
{

	@Autowired
	private UserService userService;

	@Autowired
	private MacrosRepository macrosRepository;

	@Override
	public List<Macros> findAll()
	{
		return null;
	}

	@Override
	public Macros update(Macros macros, Principal principal)
	{
		Macros currentMacros = findById(principal);

		if(macros.getMeals() != currentMacros.getMeals())
		{
			currentMacros.setMeals(macros.getMeals());
			currentMacros.setOverallMacrosPerMeal();
		}

		return macrosRepository.save(currentMacros);
	}

	@Override
	public void delete(long id)
	{

	}

	@Override
	public Macros findById(Principal principal)
	{
		User currentUser = userService.getCurrentUser(principal);

		long userid = currentUser.getUserid();

		return macrosRepository.findById(userid).orElseThrow(() -> new ResourceNotFoundException("Could not find macro " +
				"with id " + userid));
	}

	@Transactional
	@Override
	public Macros save(Macros macros, Principal principal) throws ResourceNotFoundException
	{
		return null;
	}

}
