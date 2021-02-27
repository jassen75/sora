package cc.js.sora.fight;

import cc.js.sora.fight.condition.UserCondition;

public abstract class CounterCondition extends UserCondition {

	public abstract int getMaxCount();
	
	public abstract int getDefaultCount();
	
	

}
