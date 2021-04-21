package cc.js.sora.fight.condition;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;

public class JinzhanSoldierCondition implements Condition{

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return "携带近战士兵";
	}

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
		// TODO Auto-generated method stub
		return fightInfo.getRole(isAttack).getSoldier().getRange()==1;
	}

}
