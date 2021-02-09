package cc.js.sora.fight.skill.passivity;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.NoCondition;

public class BloodBattle extends Skill {

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return Skill.BloodBattle;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "血战";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new NoCondition();
	}

	@Override
	public List<Buff> getBuffs() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Buff(BuffType.Attack,10) , new Buff(BuffType.PhysicDef, 10));
	}

	@Override
	public Scope getScope() {
		// TODO Auto-generated method stub
		return Scope.All;
	}

}
