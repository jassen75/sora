package cc.js.sora.fight.condition;

public class HasBuffCondition implements UserCondition {
	
	@Override
	public String getDesc() {
		return "拥有强化效果";
	}

	@Override
	public boolean defaultValid() {
		return true;
	}

	@Override
	public String getName() {
		return "hasbuff";
	}

}
