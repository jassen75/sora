package cc.js.sora.fight.condition.health;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.FightRole;

public abstract class ForceHealthCondition implements Condition{
	
	
	
	abstract boolean valid(int heroLife, int soldierLife, int heroLeftLife, int soldierLeftLife, int enemyHeroLife, int enemySoldierLife, int  enemyHeroLeftLife, int enemySoldierLeftLife);

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack)
	{
		FightRole attacker = fightInfo.getAttacker();
		FightRole defender = fightInfo.getDefender();
		if(isAttack)
		{
			
			return this.valid(attacker.getHeroPanel().getLife(), attacker.getSoldierPanel().getLife(), 
					attacker.getHeroLeftLife(), attacker.getSoldierLeftLife(), 
					defender.getHeroPanel().getLife(), defender.getSoldierPanel().getLife(),
					defender.getHeroLeftLife(), defender.getSoldierLeftLife());
		} else
		{
			
			return this.valid(defender.getHeroPanel().getLife(), defender.getSoldierPanel().getLife(),
					defender.getHeroLeftLife(), defender.getSoldierLeftLife(), 
					attacker.getHeroPanel().getLife(),attacker.getSoldierPanel().getLife(), attacker.getHeroLeftLife(), 
					 attacker.getSoldierLeftLife());
		}
		
	}
}
