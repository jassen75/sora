package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.EnemyFullHealthCondition;

public class AssTech3 extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "刺客科技：杀戮气息";
	}

	@Override
	public Condition getCondition() {
		return new EnemyFullHealthCondition();
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 30, Scope.Soldier));
	}
	
	public int getSkillType()
	{
		return 4;
	}

}
