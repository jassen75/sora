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
import cc.js.sora.fight.condition.health.LessHealthCondition;

public class SorceressTech4 extends Skill {

	@Override
	public long getId() {
		return Skill.SorceressTech4;
	}

	@Override
	public String getName() {
		return "法师科技：聚精会神";
	}

	@Override
	public Condition getCondition() {
		return new LessHealthCondition(90);
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 30));
	}

	@Override
	public Scope getScope() {
		return Scope.Soldier;
	}

}
