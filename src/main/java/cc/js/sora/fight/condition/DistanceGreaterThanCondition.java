package cc.js.sora.fight.condition;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;

public class DistanceGreaterThanCondition implements Condition {
	int distance;
	public DistanceGreaterThanCondition(int distance)
	{
		this.distance = distance;
	}
	
	@Override
	public String getDesc() {
		if(distance==1)
		{
			return "远程攻击时";
		}
		return "距离大于"+distance;
	}

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
		// TODO Auto-generated method stub
		return fightInfo.getDistance() > this.distance;
	}

}
