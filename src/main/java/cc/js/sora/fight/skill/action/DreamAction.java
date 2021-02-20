package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Hero;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.EnemyHeroCondition;

public class DreamAction extends Skill {

	@Override
	public long getId() {
		return Skill.Shimeng;
	}

	@Override
	public String getName() {
		return "嗜梦";
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
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Buff(BuffType.DamageInc, 20));
	}

	@Override
	public Scope getScope() {
		return Scope.All;
	}

}
