package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;

public class Shiying extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "弑影对决";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Feature("DirectToHero", true, "直击本体", Scope.Hero, false),
				new Feature("DirectToHero", true, "直击本体", Scope.EnemyHero, false));
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new UserCondition() {

			public String getName() {
				return "AttackYouxiayinji";
			}

			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "攻击游侠印记的敌人";
			}
		};
	}

}
