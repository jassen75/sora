package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.NoCondition;
import cc.js.sora.fight.condition.SimpleAttackCondition;

public class Wuleer extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "乌勒尔之弓";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, -10, Scope.Hero));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "乌勒尔之弓";
			}
			
			public int getSkillType() {
				return 4;
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new SimpleAttackCondition();
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.Range, 1, Scope.All));
			}
			
		});
	}
	
	

}
