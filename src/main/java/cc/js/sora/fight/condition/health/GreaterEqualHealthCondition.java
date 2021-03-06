package cc.js.sora.fight.condition.health;

public class GreaterEqualHealthCondition  extends ForceHealthCondition  {
	
	int percent;
	public GreaterEqualHealthCondition(int percent)
	{
		this.percent = percent;
	}
	@Override
	public String getDesc() {
		return "部队血量大于或者等于"+percent+"%";
	}
	@Override
	public boolean valid(int heroLife, int soldierLife, int heroLeftLife, int soldierLeftLife, int enemyHeroLife,
			int enemySoldierLife, int enemyHeroLeftLife, int enemySoldierLeftLife) {
		return heroLeftLife+soldierLeftLife >= (heroLife+soldierLife) * percent / 100.0;
	}

}
