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

public class SailorTech2  extends Skill {

	@Override
	public String getName() {
		return "水兵科技：空海奇袭";
	}

	@Override
	public Condition getCondition() {
		return new UserCondition() {

			@Override
			public String getDesc() {
				return "被攻击方不满血";
			}

			@Override
			public String getName() {
				return "enemyNotFull";
			}

			@Override
			public boolean defaultValid() {
				return false;
			}};
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.Attack, 20, Scope.Soldier), new Enhance(BuffType.PhysicDef, 20, Scope.Soldier));
	}
	
	public int getSkillType()
	{
		return 1;
	}

}
