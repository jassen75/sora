package cc.js.sora.fight.condition;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;

public class NotSimpleAttack implements Condition{
	
	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return "技能攻击";
	}

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
		// TODO Auto-generated method stub
		return !fightInfo.getAttacker().getAction().isSimpleAttack();
	}

}
