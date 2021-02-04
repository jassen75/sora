package cc.js.sora.fight.condition;

public class LessHealthCondition implements ForceHealthCondition  {
	
	int percent;
	public LessHealthCondition(int percent)
	{
		this.percent = percent;
	}
	@Override
	public String getDesc() {
		return "部队血量低于"+percent+"%";
	}
	@Override
	public boolean valid(int heroLife, int soldierLife, int heroLeftLife, int soldierLeftLife, int enemyHeroLife,
			int enemySoldierLife, int enemyHeroLeftLife, int enemySoldierLeftLife) {
		return heroLeftLife+soldierLeftLife< (heroLife+soldierLife) * percent / 100.0;
	}

}
