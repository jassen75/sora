package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.usercondition.AttackBeGuarded;

public class HorseTech5  extends Skill {

	@Override
	public String getName() {
		return "骑兵科技";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new AttackBeGuarded();
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
	    return Lists.newArrayList(new Enhance(BuffType.Attack, 20, Scope.Soldier), new Enhance(BuffType.Physic, 20, Scope.Soldier));
	}
	
	public int getSkillType()
	{
		return 1;
	}

}
