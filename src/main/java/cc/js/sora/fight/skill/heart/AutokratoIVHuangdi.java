package cc.js.sora.fight.skill.heart;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.MoveDistanceCondition;
import cc.js.sora.fight.condition.PhysicDamageCondition;
import cc.js.sora.fight.skill.talent.AutokratoIVTalent;

public class AutokratoIVHuangdi extends Skill {
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "奥托克拉托四世*皇帝大心";
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new MoveDistanceCondition(3, 5);
	}

	public int getSkillType() {
		return 4;
	}
	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return getEffects(3);
	}
	
	@Override
	public List<Effect> getEffects(int count) {
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, count*5, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "奥托克拉托四世*皇帝大心";
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new PhysicDamageCondition();
			}
			
			public int getSkillType() {
				return 5;
			}
			
			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
			
		}, new AutokratoIVTalent());
	}

}
