package cc.js.sora.fight.condition.health;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;

public class EnemyHealthGreaterCondition implements Condition {

	int percent;
	public EnemyHealthGreaterCondition(int percent)
	{
		this.percent = percent;
	}
	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return "敌人部队生命百分比高于"+percent+"%";
	}

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
		// TODO Auto-generated method stub
		return fightInfo.getEnemyRole(isAttack).getLifePercent() > (percent / 100.0);
	}





}
