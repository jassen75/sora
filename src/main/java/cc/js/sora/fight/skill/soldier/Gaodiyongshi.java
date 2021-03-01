package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.NoCondition;

public class Gaodiyongshi extends Skill {

	public long getId() {
		return Skill.Gaodiyongshi;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "高地勇士技能";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 15, Scope.Soldier),new Enhance(BuffType.DamageDec, 15, Scope.Soldier));
	}

}
