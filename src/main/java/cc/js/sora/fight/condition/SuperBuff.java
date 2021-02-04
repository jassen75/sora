package cc.js.sora.fight.condition;

public class SuperBuff implements UserCondition {

	@Override
	public String getDesc() {
		return "超绝强化";
	}

	@Override
	public boolean defaultValid() {
		return true;
	}

}
