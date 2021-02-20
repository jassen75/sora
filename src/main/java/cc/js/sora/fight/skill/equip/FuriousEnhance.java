package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
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
		return Lists.newArrayList(new Buff(BuffType.Attack, 10), new Buff(BuffType.DamageDec, 10));
	}

	@Override
	public Scope getScope() {
		// TODO Auto-generated method stub
		return Scope.Hero;
	}
	
	@Override
	public int getSkillType()
	{
		return 1;
	}
}
