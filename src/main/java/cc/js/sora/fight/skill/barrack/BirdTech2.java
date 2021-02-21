package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
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
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.Attack, 20, Scope.Soldier), new Enhance(BuffType.PhysicDef, 20, Scope.Soldier));
	}
	
	public int getSkillType()
	{
		return 1;
	}

}
