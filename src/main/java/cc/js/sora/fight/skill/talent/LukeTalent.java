package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.NoCondition;
import cc.js.sora.fight.condition.health.FullHealthCondition;
import cc.js.sora.fight.condition.health.LifePercentCondition;

public class LukeTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "卢克蕾蒂娅天赋";
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new LifePercentCondition(85,100);
	}
	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 30, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "卢克蕾蒂娅天赋";
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new LifePercentCondition(70,85);
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 20, Scope.All));
			}
			
		},new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "卢克蕾蒂娅天赋";
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new LifePercentCondition(40,70);
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
			
		});
	}

}
