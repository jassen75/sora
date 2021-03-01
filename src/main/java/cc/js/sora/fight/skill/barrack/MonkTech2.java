package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.GreaterHealthCondition;

public class MonkTech2 extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "僧侣科技：虔诚信仰";
	}

	@Override
	public Condition getCondition() {
		return new GreaterHealthCondition(80);
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 30, Scope.Soldier));
	}

}
