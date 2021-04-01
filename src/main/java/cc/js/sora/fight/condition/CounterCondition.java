package cc.js.sora.fight.condition;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;

public interface CounterCondition extends Condition {
	
	public int getMaxCount();
	
	public int getDefaultCount();
	
	public int getCount(FightInfo fightInfo, boolean isAttack);

}
