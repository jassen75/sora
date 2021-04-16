package cc.js.sora.fight.condition.health;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;

public class HealthLessThanEnemy implements Condition{
	
	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return "血量百分比小于对面";
	}

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
		// TODO Auto-generated method stub
		return fightInfo.getRole(isAttack).getLifePercent() < fightInfo.getEnemyRole(isAttack).getLifePercent();
	}

}
