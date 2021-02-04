package cc.js.sora.fight.condition;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Hero;

public interface EnemyHeroCondition extends Condition {
	
	boolean valid(Hero enemyHero);

}
