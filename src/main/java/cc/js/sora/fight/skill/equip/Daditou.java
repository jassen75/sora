package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.GreaterHealthCondition;

public class Daditou extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "大地之冠";
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.Magic,  15, Scope.Hero));
	}
	
	public int getSkillType() {
		return 5;
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "大地之冠";
			}
			
			@Override
			public Condition getCondition() {
				return new GreaterHealthCondition(80);
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.PhysicDamageDec,  10, Scope.Hero));
			}
			
		});
	}

}
