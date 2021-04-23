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
import cc.js.sora.fight.condition.NoCondition;
import cc.js.sora.fight.condition.health.LessHealthCondition;
import cc.js.sora.fight.skill.talent.AlustrielTalent;

public class AlustrielLangqi extends Skill {
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "艾拉斯卓*狼骑统帅大心";
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new LessHealthCondition(70);
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 10, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "艾拉斯卓*狼骑统帅大心";
			}
			
			public int getSkillType() {
				return 5;
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new DistanceCondition(1);
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
			
		}, new AlustrielTalent());
	}

}
