public class Avatar
{
	private double rank;
	private double hitPoints;
	private double stamina;
	private char characterType;

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
}
