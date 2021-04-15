package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Debuff;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.NoCondition;
import cc.js.sora.fight.condition.SimpleAttackCondition;
import cc.js.sora.fight.condition.UserCondition;

public class Toushiche extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "投石车";
	}

	public int getSkillType() {
		return 4;
	}
	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, -10, Scope.All), new Enhance(BuffType.Range, 1, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "投石车";
			}
			
			public int getSkillType() {
				return 4;
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new UserCondition() {

					@Override
					public boolean defaultValid() {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "投石车特效触发(20%概率)";
					}
					
				};
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Debuff(BuffType.Random, 0));
			}
			
		},  new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "投石车";
			}
			
			public Condition getCondition() {
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
