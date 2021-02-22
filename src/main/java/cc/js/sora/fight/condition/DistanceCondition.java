package cc.js.sora.fight.condition;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;

public class DistanceCondition implements Condition {
	
	int distance;
	public DistanceCondition(int distance)
	{
		this.distance = distance;
	}
	
	@Override
	public String getDesc() {
		if(distance==1)
		{
			return "近战攻击";
		}
		return "距离等于"+distance;
	}

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
		// TODO Auto-generated method stub
		return fightInfo.getDistance() == this.distance;
	}

}
