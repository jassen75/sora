package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.GreaterHealthCondition;

public class Benlei extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "奔雷";
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new GreaterHealthCondition(50);
	}
	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 15, Scope.All));
	}

}
