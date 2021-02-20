package cc.js.sora.fight.condition;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;

public class HasBuffCondition implements Condition {
	
	@Override
	public String getDesc() {
		return "拥有强化效果";
	}

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
	    return fightInfo.getRole(isAttack).getDebuffList()!= null && fightInfo.getRole(isAttack).getDebuffList().size() > 0;
	}

}
