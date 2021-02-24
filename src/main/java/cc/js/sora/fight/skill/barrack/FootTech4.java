package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.DistanceGreaterThanCondition;

public class FootTech4 extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "步兵科技：防空重甲";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new DistanceGreaterThanCondition(1);
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.MagicDef, 20, Scope.Soldier), new Enhance(BuffType.PhysicDef, 20, Scope.Soldier));
	}
	
	public int getSkillType()
	{
		return 4;
	}

}
