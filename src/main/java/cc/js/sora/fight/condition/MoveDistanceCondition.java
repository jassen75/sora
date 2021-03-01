package cc.js.sora.fight.condition;

import cc.js.sora.fight.CounterUserCondition;

public class MoveDistanceCondition extends CounterUserCondition {
	
	int defaultDistance;
	int maxCount; 
	public MoveDistanceCondition(int defaultDistance,int  maxCount)
	{
		this.defaultDistance = defaultDistance;
		this.maxCount = maxCount;
	}
	
	public String getName()
	{
		return "MoveDistance";
	}
	
	@Override
	public int getMaxCount() {
		// TODO Auto-generated method stub
		return maxCount;
	}

	public int getDefaultCount() {
		return defaultDistance;
	}

	@Override
	public boolean defaultValid() {
		return true;
	}

	@Override
	public String getDesc() {
		return "战斗前移动1格";
	}

}
