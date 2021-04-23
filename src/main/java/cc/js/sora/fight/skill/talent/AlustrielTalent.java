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
import cc.js.sora.fight.skill.action.Zaiyidong;

public class AlustrielTalent extends Skill  {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "艾拉斯卓天赋";
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new MoveDistanceCondition(5,5);
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return getEffects(5);
	}
	
	@Override
	public List<Effect> getEffects(int count) {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 4*count, Scope.All), new Enhance(BuffType.CriticalDamageInc, 4*count, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Zaiyidong());
	}

}
