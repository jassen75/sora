package cc.js.sora.fight.condition;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Hero;

public abstract class EnemyHeroCondition implements Condition {
	
	public abstract boolean valid(Hero enemyHero);

	@Override
	public abstract String getDesc() ;

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {

		return valid(fightInfo.getEnemyRole(isAttack).getHero());
	}

}
