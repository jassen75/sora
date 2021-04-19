package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.JinzhanSoldierCondition;

public class Jinglingzhixi extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "精灵之息";
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new JinzhanSoldierCondition();
	}
	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Range, 1, Scope.Soldier));
	}

}
