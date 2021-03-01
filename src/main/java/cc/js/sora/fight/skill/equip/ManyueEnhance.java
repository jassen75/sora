package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.GreaterHealthCondition;

public class ManyueEnhance extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "满月附魔";
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new GreaterHealthCondition(80);
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 10, Scope.Hero),
				new Enhance(BuffType.Physic, 10, Scope.Hero), new Enhance(BuffType.Intel, 10, Scope.Hero),
				new Enhance(BuffType.Magic, 10, Scope.Hero));
	}

}
