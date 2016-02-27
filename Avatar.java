public class Avatar
{
	private double rank;
	private double hitPoints;
	private double stamina;
	private String characterType;
	private double nourishment;
	private double meds;
	private double sleep;
	private double exercise;
	private boolean battling;

	public Avatar()
	{
		rank = 0; hitPoints = 0; stamina = 0;
	}
	
	public double getNourish()
	{
		return nourishment;
	}
	
	public double getMeds()
	{
		return meds;
	}
	
	public double getSleep()
	{
		return sleep;
	}
	
	public double getExercise()
	{
		return exercise;
	}

	public void changeRank(double rankAdded)
	{
		rank += rankAdded;
	}

	public boolean isBattling()
	{
		return battling;
	}
	public void changeHitPoints(double hpAdded)
	{
		hitPoints += hpAdded;
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
