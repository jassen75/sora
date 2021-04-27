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
import cc.js.sora.fight.condition.NoCondition;
import cc.js.sora.fight.condition.health.HealthLessThanEnemy;
import cc.js.sora.fight.skill.talent.HelenaTalent;
import cc.js.sora.fight.skill.talent.RanfordTalent;

public class RanfordQishi extends Skill {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "兰芳特*骑士统帅大心";
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new MoveDistanceCondition(3, 5);
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return getEffects(3);
	}
	
	@Override
	public List<Effect> getEffects(int count) {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 5*count, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "兰芳特*骑士统帅大心";
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new HealthLessThanEnemy();
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
			
		}, new RanfordTalent());
	}

}
