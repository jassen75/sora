package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.MoveDistanceCondition;
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.skill.action.Qishijingshen;

public class HelenaTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "海伦娜天赋";
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new MoveDistanceCondition(3,5);
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
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 4*count, Scope.All));

	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "海伦娜天赋";
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
						return "周围两格没有队友";
					}
					
				};
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.Physic, 25, Scope.All));
			}
			
		}, new Qishijingshen());
	}
}
