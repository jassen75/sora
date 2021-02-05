package cc.js.sora.fight.skill;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.HasBuffCondition;

public class TowaTalent extends Skill  {

	@Override
	public long getId() {
		return Skill.TowaTalent;
	}

	@Override
	public String getName() {
		return "托娃天赋";
	}

	@Override
	public Condition getCondition() {
		return new HasBuffCondition();
	}

	@Override
	public List<Buff> getBuffs() {
		return Lists.newArrayList(new Buff(BuffType.Attack, 15), new Buff(BuffType.PhysicDef, 15));
	}

	@Override
	public Scope getScope() {
		return Scope.All;
	}

}
