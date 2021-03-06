package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Counter;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Hero;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.EnemyHeroCondition;

public class PatyleTalent extends Skill {
	
	@Override
	public long getId() {
		return Skill.PatyleTalent;
	}

	@Override
	public String getName() {
		return "帕希尔天赋";
	}

	@Override
	public Condition getCondition() {
		return new EnemyHeroCondition() {

			@Override
			public boolean valid(Hero enemyHero) {
				return !enemyHero.isWoman();
			}

			@Override
			public String getDesc() {
				return "对方英雄不是女性";
			}};
	}

	@Override
	public List<Effect> getEffects() {
		//return Lists.newArrayList(new Enhance(BuffType.AttackCounter, 25, Scope.All), new Enhance(BuffType.DamageDec, 25, Scope.All));
		return Lists.newArrayList(new Counter(BuffType.Attack, 25, 0, Scope.All), new Enhance(BuffType.DamageDec, 25, Scope.All));
	}


}
