package cc.js.sora.fight.condition;

import cc.js.sora.fight.CounterUserCondition;

public class EnemyMoveDistanceCondition extends CounterUserCondition  {
	
	int defaultDistance;
	int maxCount; 
	public EnemyMoveDistanceCondition(int defaultDistance,int  maxCount)
	{
		this.defaultDistance = defaultDistance;
		this.maxCount = maxCount;
	}
	
	public String getName()
	{
		return "EnemyMoveDistanceCondition";
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
		return "敌人战斗前移动1格";
	}

}
