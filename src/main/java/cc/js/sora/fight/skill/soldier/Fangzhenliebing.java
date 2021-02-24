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

public class Fangzhenliebing extends Skill{
	
	public long getId() {
		return Skill.Fangzhenliebing;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "方阵列兵技能";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new NoCondition();
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 20, Scope.Soldier),new Enhance(BuffType.PhysicDef, 20, Scope.Soldier));
	}
	
	public List<Skill> childSkill()
	{
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "方阵列兵技能";
			}

			@Override
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new NoCondition();
			}

			public int getSkillType()
			{
				return 4;
			}
			
			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 20, Scope.Soldier));
			}});
	}

}
