public class Avatar
{
	private double rank;
	private double hitPoints;
	private double stamina;
	private String characterType;
	private double nourishment, fatsOilsSweets, fruit, vegs, grains, meats, dairy;
	private double meds, medsTaken;
	private int medsPerDay;
	private double sleep, sleepHours;
	private double exercise, exerciseTime;
	private boolean battling;

	public Avatar()
	{
		rank = 0; hitPoints = 0; stamina = 0;
	}
	
	public double getNourish()
	{
		nourishment = fruit + vegs + grains + meats + dairy - fatsOilsSweets;
		return nourishment;
	}
	
	public double getMeds()
	{
		if (medsPerDay == medsTaken)
			meds = 40;
		else meds = 0;
		return meds;
	}
	
	public double getSleep()
	{
		if (sleepHours >= 11)
		{
			sleep = 20 - 5*(sleepHours - 10);
			return sleep;
		}
		else
		{
			sleep = 2*sleepHours;
			return sleep;
		}
	        
	}
	
	public double getExercise()
	{
		exercise = exerciseTime / 3;
		return exercise;
	}

	public boolean isBattling()
	{
		return battling;
	}
	
	public void changeHitPoints(double hpAdded)
	{
		hitPoints += hpAdded;
	}
	
	public void changeRank(double rankAdded)
	{
		rank += rankAdded;
	}
	
	public void changeFruit(double fr)
	{
		fruit = fr;
		if (fruit > 3) fruit = 3;
	}
	
	public void changeVegs(double v)
	{
		vegs = v;
		if (vegs > 5) vegs = 5;
	}
	
	public void changeMeats(double m)
	{
		meats = m;
		if (meats > 4) meats = 4;
	}
	
	public void changeDairy(double d)
	{
		dairy = d;
		if (dairy > 3) dairy = 3;
	}
	
	public void changeGrains(double g)
	{
		grains = g;
		if (grains > 6) grains = 6;
	}
	
	public void changeSweets(double s)
	{
		fatsOilsSweets = s;
	}
	
	
	public void changeMedsPerDay(double medsAdded)
	{
		medsPerDay = medsAdded;
	}
	
	public void changeExerciseTime(double tAdded)
	{
		exerciseTime = tAdded;
	}
	
	public void changeSleepHours(double sleepHrsAdded)
	{
		sleepHours - sleepHrsAdded;
	}
	
	public double getHitPoints()
	{
		return hitPoints;
	}
	
	public double getStamina()
	{
		return stamina;
	}

	public double getRank()
	{
		return rank;
	}
	
	public String getType()
	{
		return characterType;
	}
}
