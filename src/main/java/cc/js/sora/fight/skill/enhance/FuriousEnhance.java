package cc.js.sora.fight.skill.enhance;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.NoCondition;
import cc.js.sora.fight.condition.UserCondition;

public class FuriousEnhance extends Skill {


	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return Skill.FuriousEnhance;
	}

	@Override
	public String getName() {
		return "怒涛附魔";
	}

	@Override
	public Condition getCondition() {
		return new NoCondition();
	}

	@Override
	public List<Effect> getEffects()  {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 10, Scope.Hero), new Enhance(BuffType.DamageDec, 10, Scope.Hero));
	}
	
	@Override
	public int getSkillType()
	{
		return 1;
	}
}
