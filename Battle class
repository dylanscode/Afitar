public class Battle
{
	private Avatar avatar1;
	private Avatar avatar2;
	private double points;
	private int priority; //who gets to attack first
	private double dmg;

	while (true)
	{
		if (avatar1.getStamina() > avatar2.getStamina())
			priority = 1;
		else if (avatar2.getStamina() > avatar1.getStamina())
			priority = 2;

		switch (priority)
		{
			
			case 1: 
				{
					dmg = avatar2.getNourish() + avatar2.getMeds() +             avatar2.getSleep();
					avatar2.changeHitPoints(dmg * -1);
					if (avatar2.getHitPoints() <= 0) break;
				}
			case 2:
				{
dmg = avatar1.getNourish() + avatar1.getMeds() + avatar1.getSleep();
avatar1.addHitPoints(dmg * -1);
if (avatar1.getHitPoints() <= 0) break;
                                    }	
		}
					

	if (avatar1.getHitPoints() == 0)
	{
		avatar2.addRank(points);
		avatar1.addRank(points * -1);
	}
	else if (avatar2.getHitPoints() == 0)
	{
		avatar1.addRank(points);
		avatar2.addRank(points * -1);
	}
}
