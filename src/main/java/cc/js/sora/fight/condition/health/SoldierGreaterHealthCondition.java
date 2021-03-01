package cc.js.sora.fight.condition.health;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		log.info("soldierLeftLife=="+soldierLeftLife);
		if(soldierLife > 0)
		{
			log.info("percent:"+soldierLeftLife*100.0/soldierLife);
		}else
		{
			log.info("percent: soldierLife====0");
		}
		return soldierLeftLife > soldierLife * percent / 100.0;
	}

}
