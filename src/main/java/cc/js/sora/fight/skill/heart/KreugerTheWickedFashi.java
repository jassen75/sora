package cc.js.sora.fight.skill.heart;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.DistanceGreaterThanCondition;
import cc.js.sora.fight.condition.health.LessHealthCondition;
import cc.js.sora.fight.skill.talent.KreugerTheWickedTalent;

public class KreugerTheWickedFashi extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "邪神库鲁加*裂心法师大心";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 10, Scope.All));
	}
	
	public int getSkillType() {
		return 5;
	}
	

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new DistanceGreaterThanCondition(1);
	}

	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "邪神库鲁加*裂心法师大心";
			}

			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new LessHealthCondition(70);
			}
			
			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
			
		},new KreugerTheWickedTalent());
	}


}
