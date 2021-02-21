package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;

public class BirdTech3 extends Skill {

	@Override
	public long getId() {
		return Skill.BirdTech3;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "飞兵科技：特技飞行";
	}

	@Override
	public Condition getCondition() {
		return new UserCondition() {

			@Override
			public String getDesc() {
				return "身处防御地形";
			}

			@Override
			public String getName() {
				return "DefensiveTerrain";
			}

			@Override
			public boolean defaultValid() {
				return false;
			}};
	}

	@Override
	public List<Effect> getEffects()  {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 20));
	}

	@Override
	public Scope getScope() {
		return Scope.Soldier;
	}

}
