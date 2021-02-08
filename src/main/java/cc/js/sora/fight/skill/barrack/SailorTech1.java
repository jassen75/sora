package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.GreaterHealthCondition;

public class SailorTech1  extends Skill {

	@Override
	public long getId() {
		return Skill.SailorTech1;
	}

	@Override
	public String getName() {
		return "水兵科技：先制打击";
	}

	@Override
	public Condition getCondition() {
		return new GreaterHealthCondition(80);
	}

	@Override
	public List<Buff> getBuffs() {
		return Lists.newArrayList(new Buff(BuffType.Attack, 20), new Buff(BuffType.PhysicDef, 20));
	}

	@Override
	public Scope getScope() {
		return Scope.Soldier;
	}

}
