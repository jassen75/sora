package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.SoldierGreaterHealthCondition;

public class Shengtianma extends Skill {
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "圣天马";
	}
	
	public int getSkillType() {
		return 4;
	}

	
	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new SoldierGreaterHealthCondition(50);
	}
	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 50, Scope.Soldier));
	}

}
