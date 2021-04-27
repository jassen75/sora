package cc.js.sora.fight.condition;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;

public class HasNoCounterCondition implements Condition {

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return "没有克制关系";
	}

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
		// TODO Auto-generated method stub
		return !fightInfo.hasCounterRelation();
	}

}
