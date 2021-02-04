package cc.js.sora.fight.skill;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Hero;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.EnemyHeroCondition;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PatyleTalent extends Skill {
	
	
	public PatyleTalent()
	{
		this.name = "PatyleTalent";
		this.buffs.add(new Buff(BuffType.Counter, 25));
		this.condition = new EnemyHeroCondition() {

			@Override
			public boolean valid(Hero enemyHero) {
				return !enemyHero.isWoman();
			}

			@Override
			public String getDesc() {
				return "对方英雄不是女性";
			}};
	}
	

}
