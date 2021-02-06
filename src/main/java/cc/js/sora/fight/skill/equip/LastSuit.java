package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.FullHealthCondition;

public class LastSuit extends Skill{

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return Skill.Zuihouzhifu;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "最后之服技能";
	}

	@Override
	public Condition getCondition() {
		return new FullHealthCondition() ;
	}

	@Override
	public List<Buff> getBuffs() {
		return Lists.newArrayList(new Buff(BuffType.DamageDec, 40));
	}

	@Override
	public Scope getScope() {
		return Scope.Hero;
	}

}
