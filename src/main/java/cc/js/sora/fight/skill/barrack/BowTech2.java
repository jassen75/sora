package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.EnemyFullHealthCondition;

public class BowTech2 extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "弓箭科技：暗影打击";
	}

	@Override
	public Condition getCondition() {
		return new EnemyFullHealthCondition();
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.Attack, 20, Scope.Soldier), new Enhance(BuffType.Physic, 20, Scope.Soldier));
	}
	
	public int getSkillType()
	{
		return 1;
	}
}
