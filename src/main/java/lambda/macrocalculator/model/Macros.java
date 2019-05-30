package lambda.macrocalculator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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

	public Macros(int inches, long protein, long carb, long fat, long calories, String meals, User user)
	{
		this.inches = inches;
		this.protein = protein;
		this.carb = carb;
		this.fat = fat;
		this.calories = calories;
		this.meals = meals;
		this.user = user;
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

	public void setOverallMacrosPerMeal(String mealPlan)
	{
		switch (mealPlan.toLowerCase()) {
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

	public void setProtein(long protein)
	{
		this.protein = protein;
	}

	public long getCarb()
	{
		return carb;
	}

	public void setCarb(long carb)
	{
		this.carb = carb;
	}

	public long getFat()
	{
		return fat;
	}

	public void setFat(long fat)
	{
		this.fat = fat;
	}

	public long getCalories()
	{
		return calories;
	}

	public void setCalories(long calories)
	{
		this.calories = calories;
	}

	public int getInches()
	{
		return inches;
	}

	public void setInches(int inches)
	{
		this.inches = inches;
	}
}
