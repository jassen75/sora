package cc.js.sora.fight.condition;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;

public class FixSoldierCondition implements Condition {

	@Override
	public String getDesc() {
		return "混合兵种";
	}

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
		return fightInfo.getRole(isAttack).getHero().getType() != fightInfo.getRole(isAttack).getSoldier().getType();
	}

}
