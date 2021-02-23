package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;

public class FootTech5  extends Skill {

	@Override
	public String getName() {
		return "步兵科技：孤军奋战";
	}

	@Override
	public Condition getCondition() {
		return new UserCondition() {

			@Override
			public boolean defaultValid() {
				return false;
			}

			@Override
			public String getDesc() {
				return "周围1格没有队友";
			}};
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 30, Scope.Soldier));
	}

}
