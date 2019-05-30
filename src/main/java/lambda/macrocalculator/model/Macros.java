package lambda.macrocalculator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JsonIgnoreProperties("macros")
	private User user;

	public Macros()
	{
	}

	public Macros(int inches, long protein, long carb, long fat, long calories, User user)
	{
		this.inches = inches;
		this.protein = protein;
		this.carb = carb;
		this.fat = fat;
		this.calories = calories;
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
