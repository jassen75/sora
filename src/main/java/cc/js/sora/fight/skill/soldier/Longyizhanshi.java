package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.MoveDistanceCondition;

public class Longyizhanshi extends Skill{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "龙弈战士";
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new MoveDistanceCondition(3, 5);
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
		return Lists.newArrayList(new Enhance(BuffType.Attack, count*6, Scope.Soldier),new Enhance(BuffType.DamageDec, count*6, Scope.Soldier));
	}

}
