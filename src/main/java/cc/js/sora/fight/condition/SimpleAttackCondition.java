package cc.js.sora.fight.condition;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;

public class SimpleAttackCondition implements Condition{

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return "普通攻击";
	}

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
		// TODO Auto-generated method stub
		return fightInfo.getAttacker().getAction().isSimpleAttack();
	}

}
