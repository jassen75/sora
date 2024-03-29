package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.EnemyMoveDistanceCondition;

public class AutokratoIVTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "奥托克拉托四世天赋";
	}

	public int getSkillType() {
		return 3;
	}
	
	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.Attack, 20, Scope.All), new Enhance(BuffType.Physic, 20, Scope.All),
				new Enhance(BuffType.Magic, 20, Scope.All), new Enhance(BuffType.Tech, 20, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "奥托克拉托四世天赋";
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new EnemyMoveDistanceCondition(3,5);
			}

			public int getSkillType() {
				return 5;
			}
			
			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return getEffects(3);
			}
			
			@Override
			public List<Effect> getEffects(int count) {
				return Lists.newArrayList(new Enhance(BuffType.DamageDec, count*4, Scope.All));
			}
			
		});
	}

}
