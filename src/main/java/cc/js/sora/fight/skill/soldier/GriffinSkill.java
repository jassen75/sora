package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.SoldierGreaterHealthCondition;

public class GriffinSkill extends Skill{

	@Override
	public long getId() {
		return Skill.HuangjiashijiuSkill;
	}

	@Override
	public String getName() {
		return "皇家狮鹫技能";
	}

	@Override
	public Condition getCondition() {
		return new SoldierGreaterHealthCondition(80);
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.Attack, 30, Scope.Soldier), new Enhance(BuffType.PhysicDef, 30, Scope.Soldier));
	}


}
