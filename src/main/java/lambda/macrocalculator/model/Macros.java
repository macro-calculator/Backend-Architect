package lambda.macrocalculator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lambda.macrocalculator.exception.ResourceNotFoundException;

import javax.persistence.*;

@Entity
@Table(name = "macros")
public class Macros extends Auditable
{
	@Id
	private long macroid;

	@Column(nullable = false)
	private int inches;
	private long protein;
	private long carb;
	private long fat;
	private long calories;
	private String meals;
	private long proteinPerMeal;
	private long fatsPerMeal;
	private long carbsPerMeal;
	private long proteinPerSnack;
	private long fatsPerSnack;
	private long carbsPerSnack;


	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JsonIgnoreProperties("macros")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private User user;

	public Macros()
	{
	}

	public Macros(User user, int inches)
	{

		long bmr;
		this.user = user;
		this.inches = inches;
		this.meals = "4 meals a day";
		setProtein();
		setCarb();
		setFat();
		setCalories();
		setOverallMacrosPerMeal();
	}

	public long getMacroid()
	{
		return macroid;
	}

	public void setMacroid(long macroid)
	{
		this.macroid = macroid;
	}

	public String getMeals()
	{
		return meals;
	}

	public void setMeals(String meals)
	{
		this.meals = meals;
	}

	public long getProteinPerMeal()
	{
		return proteinPerMeal;
	}

	public void setProteinPerMeal(long proteinPerMeal)
	{
		this.proteinPerMeal = proteinPerMeal;
	}

	public long getFatsPerMeal()
	{
		return fatsPerMeal;
	}

	public void setFatsPerMeal(long fatsPerMeal)
	{
		this.fatsPerMeal = fatsPerMeal;
	}

	public void setOverallMacrosPerMeal()
	{
		switch (meals.toLowerCase()) {
			case "4 meals a day":
				setFatsPerMeal(getFat() / 4);
				setProteinPerMeal(getProtein() / 4);
				setCarbsPerMeal(getCarb() / 4);
				break;
			case "3 meals a day":
				setFatsPerMeal(getFat() / 3);
				setProteinPerMeal(getProtein() / 3);
				setCarbsPerMeal(getCarb() / 3);
				break;
			case "3 meals and 2 snacks":
				long fatsPerSn = getFat() / 8;
				long carbsPerSn = getCarb() / 8;
				long proteinPerSn = getProtein() / 8;
				setFatsPerSnack(fatsPerSn);
				setCarbsPerSnack(carbsPerSn);
				setProteinPerSnack(proteinPerSn);
				setFatsPerMeal(fatsPerSn * 2);
				setCarbsPerMeal(carbsPerSn * 2);
				setProteinPerMeal(proteinPerSn * 2);
				break;

		}
	}

	public long getCarbsPerMeal()
	{
		return carbsPerMeal;
	}

	public void setCarbsPerMeal(long carbsPerMeal)
	{
		this.carbsPerMeal = carbsPerMeal;
	}

	public long getProteinPerSnack()
	{
		return proteinPerSnack;
	}

	public void setProteinPerSnack(long proteinPerSnack)
	{
		this.proteinPerSnack = proteinPerSnack;
	}

	public long getFatsPerSnack()
	{
		return fatsPerSnack;
	}

	public void setFatsPerSnack(long fatsPerSnack)
	{
		this.fatsPerSnack = fatsPerSnack;
	}

	public long getCarbsPerSnack()
	{
		return carbsPerSnack;
	}

	public void setCarbsPerSnack(long carbsPerSnack)
	{
		this.carbsPerSnack = carbsPerSnack;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public long getProtein()
	{
		return protein;
	}

	public void setProtein()
	{
		this.protein = Math.round(getTotalDailyCals() * 0.075);
	}

	public long getCarb()
	{
		return carb;
	}

	public void setCarb()
	{
		this.carb = Math.round(getTotalDailyCals() * 0.1);;
	}

	public long getFat()
	{
		return fat;
	}

	public void setFat()
	{
		this.fat = Math.round(getTotalDailyCals() * 0.033);
	}

	public long getCalories()
	{
		return calories;
	}

	public void setCalories()
	{
		this.calories = getTotalDailyCals();
	}

	public int getInches()
	{
		return inches;
	}

	public void setInches(int inches)
	{
		this.inches = inches;
	}

	@JsonIgnore
	public long getTotalDailyCals()
	{

		long bmr;

		if(user.getGender().equals("M"))
		{
			bmr = Math.round(66 + (6.23 * user.getCurrentweight())
							+ (12.7 * getInches())
							- (6.8 * user.getAge()));
		} else {
			bmr =
					Math.round(655 + (4.35 * user.getCurrentweight())
							+ (4.7 * getInches())
							- (4.5 * user.getAge()));

		}

		long tdee = 0;

		switch (user.getActivitylevel().toLowerCase()) {
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

		switch (user.getGoal().toLowerCase()) {
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

		return totalDailyCalories;
	}
}
