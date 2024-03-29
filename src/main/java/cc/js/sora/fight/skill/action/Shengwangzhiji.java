package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.usercondition.AttackBeGuarded;

public class Shengwangzhiji extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "圣王直击";
	}
	
	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new AttackBeGuarded();
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 15, Scope.All));
	}
	
	public int getSkillType() {
		return 4;
	}

}
