package cc.js.sora.fight.skill.heart;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.MagicDamageCondition;
import cc.js.sora.fight.condition.NoCondition;
import cc.js.sora.fight.condition.health.FullHealthCondition;
import cc.js.sora.fight.skill.talent.HelenaTalent;
import cc.js.sora.fight.skill.talent.RachelTalent;

public class RachelMoneng extends Skill {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "蕾伽尔*魔能核心大心";
	}
	
	public int getSkillType() {
		return 5;
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new MagicDamageCondition();
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
				return "蕾伽尔*魔能核心大心";
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new FullHealthCondition();
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
			
		}, new RachelTalent());
	}


}
