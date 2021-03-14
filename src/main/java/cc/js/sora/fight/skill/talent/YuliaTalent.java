package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.GreaterEqualHealthCondition;
import cc.js.sora.fight.skill.passivity.Xinyang;

public class YuliaTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "尤利娅天赋";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 30, Scope.All));
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new GreaterEqualHealthCondition(70);
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Xinyang());
	}
	
	

}
