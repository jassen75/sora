package cc.js.sora.fight.condition.health;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;

public class LifePercentCondition implements Condition {

	public LifePercentCondition(int min, int max) {
		this.min = min;
		this.max = max;
	}

	int min;
	int max;

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return "血量百分比在" + min + "%和" + max + "%之间";
	}

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
		// TODO Auto-generated method stub
		return fightInfo.getRole(isAttack).getLifePercent() > min / 100.0
				&& fightInfo.getRole(isAttack).getLifePercent() <= max / 100.0;
	}

}
