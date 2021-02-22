package cc.js.sora.fight.condition;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;

public class DistanceLessThanCondition implements Condition{

	int distance;
	public DistanceLessThanCondition(int distance)
	{
		this.distance = distance;
	}
	
	@Override
	public String getDesc() {
		return "距离小于"+distance;
	}

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
		// TODO Auto-generated method stub
		return fightInfo.getDistance() < this.distance;
	}

}
