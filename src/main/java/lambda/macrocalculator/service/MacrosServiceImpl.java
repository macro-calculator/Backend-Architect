package lambda.macrocalculator.service;

import lambda.macrocalculator.exception.ResourceNotFoundException;
import lambda.macrocalculator.model.ErrorDetail;
import lambda.macrocalculator.model.Macros;
import lambda.macrocalculator.model.User;
import lambda.macrocalculator.repos.MacrosRepository;
import lambda.macrocalculator.repos.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
	public Macros update(Macros macros)
	{
		return null;
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
		//TODO data is returning, but not being calculated
		User currentUser = userService.getCurrentUser(principal);

		Macros newMacros = new Macros();

		newMacros.setUser(currentUser);
		newMacros.setInches(macros.getInches());
		newMacros.setMeals(macros.getMeals());

		long bmr;

		if(currentUser.getGender().equals("M"))
		{
			bmr = Math.round(66 + (6.23 * currentUser.getCurrentweight())
							+ (12.7 * macros.getInches())
							- (6.8 * currentUser.getAge()));
		} else {
			bmr =
					Math.round(655 + (4.35 * currentUser.getCurrentweight())
							+ (4.7 * macros.getInches())
							- (4.5 * currentUser.getAge()));

		}

		long tdee = 0;

		switch (currentUser.getActivitylevel().toLowerCase()) {
			case "0 days":
				tdee = Math.round(1.2 * bmr);
				break;
			case "1-2 days":
				tdee = Math.round(1.375 * bmr);
				break;
			case "3-4 days":
				tdee = Math.round(1.55 * bmr);
				break;
			case "5-6 days":
				tdee = Math.round(1.725 * bmr);
				break;
			case "7 days":
				tdee = Math.round(1.9 * bmr);
				break;
			default:
				throw new ResourceNotFoundException("Activity level does not match format *int days*");
		}

		long totalDailyCalories;

		switch (currentUser.getGoal().toLowerCase()) {
			case "aggressive weight loss":
				totalDailyCalories = Math.round(tdee * 0.8);
				break;
			case "moderate weight loss":
				totalDailyCalories = Math.round(tdee * 0.85);
				break;
			case "weight loss":
				totalDailyCalories = Math.round(tdee * 0.9);
				break;
			case "maintain weight":
				totalDailyCalories = tdee;
				break;
			case "moderate weight gain":
				totalDailyCalories = Math.round(tdee * 1.1);
				break;
			case "aggressive weight gain":
				totalDailyCalories = Math.round(tdee * 1.15);
				break;
			default:
				throw new ResourceNotFoundException("Goal does not match format. Please make sure your goal is valid");

		}

		long totalProtein = Math.round(totalDailyCalories * 0.075);
		long totalCarbs = Math.round(totalDailyCalories * 0.1);
		long totalFat = Math.round(totalDailyCalories * 0.033);

		newMacros.setCalories(totalDailyCalories);
		newMacros.setProtein(totalProtein);
		newMacros.setCarb(totalCarbs);
		newMacros.setFat(totalFat);

		newMacros.setOverallMacrosPerMeal(newMacros.getMeals());

		return macrosRepository.save(newMacros);
	}
}
