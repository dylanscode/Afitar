public class Avatar
{
	private double rank;
	private double hitPoints;
	private double stamina;
	char characterType;

	public Avatar()
	{
		rank, hitPoints, stamina = 0;
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

	public double getRank()
	{
		return rank;
	}
}
