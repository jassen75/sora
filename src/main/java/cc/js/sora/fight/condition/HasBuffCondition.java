package cc.js.sora.fight.condition;

import cc.js.sora.fight.FightInfo;

public class HasBuffCondition implements UserCondition {
	
	@Override
	public String getDesc() {
		return "拥有强化效果";
	}

	@Override
	public boolean defaultValid() {
		return true;
	}

	@Override
	public String getName() {
		return "hasbuff";
	}

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
		// TODO Auto-generated method stub
		return false;
	}

}
