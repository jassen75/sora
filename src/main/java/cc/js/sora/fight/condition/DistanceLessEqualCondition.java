package cc.js.sora.fight.condition;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;

public class DistanceLessEqualCondition implements Condition{

	int distance;
	public DistanceLessEqualCondition(int distance)
	{
		this.distance = distance;
	}
	
	@Override
	public String getDesc() {
		return "距离小于等于"+distance;
	}

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
		// TODO Auto-generated method stub
		return fightInfo.getDistance() <= this.distance;
	}

}
