package cc.js.sora.fight.condition.health;

public class FullHealthCondition extends ForceHealthCondition {
	

	@Override
	public boolean valid(int heroLife, int soldierLife, int heroLeftLife, int soldierLeftLife,
			int enemyHeroLife, int enemySoldierLife, int enemyHeroLeftLife, int enemySoldierLeftLife) {
		return heroLife == heroLeftLife && soldierLife == soldierLeftLife;
	}

	@Override
	public String getDesc() {
		return "部队血量100%";
	}

}
