package cc.js.sora.fight.condition.health;

import cc.js.sora.fight.Condition;

public interface ForceHealthCondition extends Condition{
	
	
	
	boolean valid(int heroLife, int soldierLife, int heroLeftLife, int soldierLeftLife, int enemyHeroLife, int enemySoldierLife, int  enemyHeroLeftLife, int enemySoldierLeftLife);

}
