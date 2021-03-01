package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.DefLandCondition;

public class BowTech3 extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "弓箭科技：密林游侠";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new DefLandCondition();
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 30, Scope.Soldier));
	}
	
	public List<Skill> childSkill()
	{
		return Lists.newArrayList(new BowTech5());
	}

}
