package cc.js.sora.fight.skill.heart;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.FullHealthCondition;
import cc.js.sora.fight.skill.talent.FlorentiaTalent;

public class FlorentiaJunshi extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "弗洛朗蒂娅*军师大心";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new FullHealthCondition();
	}
	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 10, Scope.All), new Enhance(BuffType.DamageInc, 10, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new FlorentiaTalent());
	}

}
