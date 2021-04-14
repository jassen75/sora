package cc.js.sora.fight.condition;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;

public class PhysicDamageCondition implements Condition {
	
	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return "受到物理伤害";
	}

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
		// TODO Auto-generated method stub
		return fightInfo.getEnemyRole(isAttack).getHeroPanel().getAttackType() == 1;
	}

}
