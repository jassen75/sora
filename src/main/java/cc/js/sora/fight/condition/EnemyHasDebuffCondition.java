package cc.js.sora.fight.condition;

public class EnemyHasDebuffCondition implements UserCondition{
	
	@Override
	public String getDesc() {
		return "敌人拥有弱化效果";
	}

	@Override
	public boolean defaultValid() {
		return false;
	}

	@Override
	public String getName() {
		return "hasdebuff";
	}

}
