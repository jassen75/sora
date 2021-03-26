package cc.js.sora.fight.skill.passivity;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.condition.health.GreaterHealthCondition;

public class Xinyang extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "信仰";
	}

	public Condition getCondition() {
		return new UserCondition() {

			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "装备被动技能信仰";
			}
			
		};
	}
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 15, Scope.All));
	}

}
