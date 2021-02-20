package cc.js.sora.fight.condition.health;

public class SoldierGreaterHealthCondition extends ForceHealthCondition  {
	
	int percent;
	public SoldierGreaterHealthCondition(int percent)
	{
		this.percent = percent;
	}
	@Override
	public String getDesc() {
		return "士兵血量大于"+percent+"%";
	}
	@Override
	public boolean valid(int heroLife, int soldierLife, int heroLeftLife, int soldierLeftLife, int enemyHeroLife,
			int enemySoldierLife, int enemyHeroLeftLife, int enemySoldierLeftLife) {
		return soldierLeftLife > soldierLife * percent / 100.0;
	}

}
