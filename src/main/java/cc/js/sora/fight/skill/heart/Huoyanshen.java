package cc.js.sora.fight.skill.heart;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.DistanceCondition;
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.skill.talent.RyouTalent;

public class Huoyanshen extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "真田辽*火焰神大心";
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new UserCondition() {

			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "在危险范围内";
			}
			
		};
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.CriticalProbDec, 10, Scope.All));
	}

	public List<Skill> childSkill() {
		return Lists.newArrayList(new RyouTalent(),new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "真田辽*火焰神大心";
			}
			
			public int getSkillType() {
				return 5;
			}
			
			public Condition getCondition() {
				return new DistanceCondition(1);
			}
			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
			
		});
	}
}
