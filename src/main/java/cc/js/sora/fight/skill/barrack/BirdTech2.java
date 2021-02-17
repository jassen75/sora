package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.condition.health.EnemyFullHealthCondition;

public class BirdTech2 extends Skill {

	@Override
	public long getId() {
		return Skill.BirdTech2;
	}

	@Override
	public String getName() {
		return "飞兵科技：空海奇袭";
	}

	@Override
	public Condition getCondition() {
		return new EnemyFullHealthCondition();
	}

	@Override
	public List<Buff> getBuffs() {
		return Lists.newArrayList(new Buff(BuffType.Attack, 20), new Buff(BuffType.PhysicDef, 20));
	}

	@Override
	public Scope getScope() {
		return Scope.Soldier;
	}
	
	public int getSkillType()
	{
		return 1;
	}

}
