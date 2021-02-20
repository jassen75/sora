package cc.js.sora.fight.condition.health;

public class EnemyFullHealthCondition extends ForceHealthCondition {

	@Override
	public String getDesc() {
		return "敌人部队满血";
	}

	@Override
	public boolean valid(int heroLife, int soldierLife, int heroLeftLife, int soldierLeftLife, int enemyHeroLife,
			int enemySoldierLife, int enemyHeroLeftLife, int enemySoldierLeftLife) {
		// TODO Auto-generated method stub
		return enemyHeroLife == enemyHeroLeftLife && enemySoldierLife == enemySoldierLeftLife;
	}

}
