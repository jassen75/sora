package cc.js.sora.fight.condition;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;

public class EnemyHasDebuffCondition implements Condition {
	
	@Override
	public String getDesc() {
		return "敌人拥有弱化效果";
	}

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
		
		return fightInfo.getEnemyRole(isAttack).getDebuffList()!= null && fightInfo.getEnemyRole(isAttack).getDebuffList().size() > 0;
	}

}
