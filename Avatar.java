public class Avatar
{
	private double rank;
	private double hitPoints;
	private double stamina;
	private String characterType;

	public Avatar()
	{
		rank = 0; hitPoints = 0; stamina = 0;
	}

	public void changeRank(double rankAdded)
	{
		rank += rankAdded;
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
