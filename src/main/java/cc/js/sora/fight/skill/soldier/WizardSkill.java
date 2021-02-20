package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.FullHealthCondition;

public class WizardSkill extends Skill {
	@Override
	public long getId() {
		return Skill.MonvSkill;
	}

	@Override
	public String getName() {
		return "魔女技能";
	}

	@Override
	public Condition getCondition() {
		return new FullHealthCondition();
	}

	@Override
	public List<Effect> getEffects()  {
		return Lists.newArrayList(new Buff(BuffType.Attack, 45), new Buff(BuffType.MagicDef, 45));
	}

	@Override
	public Scope getScope() {
		return Scope.Soldier;
	}

}
