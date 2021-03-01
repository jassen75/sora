package cc.js.sora.fight.condition;

import cc.js.sora.fight.CounterUserCondition;

public class DefaultCounterUserCondition extends CounterUserCondition implements CounterCondition {

	private int max;
	private int def;
	private String desc;
	
	public DefaultCounterUserCondition(int max, int def, String desc)
	{
		this.max = max;
		this.def = def;
		this.desc = desc;
	}
	
	@Override
	public int getMaxCount() {
		// TODO Auto-generated method stub
		return max;
	}

	@Override
	public int getDefaultCount() {
		// TODO Auto-generated method stub
		return def;
	}

	@Override
	public boolean defaultValid() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return desc;
	}

}
