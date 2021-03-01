package cc.js.sora.fight;

import java.util.Map;

import cc.js.sora.fight.condition.CounterCondition;
import cc.js.sora.fight.condition.UserCondition;

public abstract class CounterUserCondition extends UserCondition implements CounterCondition {

	public abstract int getMaxCount();
	
	public abstract int getDefaultCount();
	
	public int getCount(FightRole fightRole)
	{
		Map<String, Integer> buffCounts = fightRole.getBuffCounts();
		if(buffCounts.containsKey(this.getName()))
		{
			return buffCounts.get(this.getName());
		} 
		
		return 0;
	}
	
	

}
