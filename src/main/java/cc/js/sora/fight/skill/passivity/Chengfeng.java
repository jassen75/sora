package cc.js.sora.fight.skill.passivity;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.CounterUserCondition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.MoveDistanceCondition;

public class Chengfeng extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "乘风";
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new MoveDistanceCondition(4, 5);
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return getEffects(4);
	}
	
	@Override
	public List<Effect> getEffects(int count) {
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, count*5, Scope.All));
	}
		


}
