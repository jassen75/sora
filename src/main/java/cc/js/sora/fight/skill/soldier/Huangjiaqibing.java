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

public class Huangjiaqibing extends Skill{
	
	public long getId() {
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "皇家骑兵技能";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new SoldierGreaterHealthCondition(80);
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.PhysicDamageDec, 45, Scope.Soldier));
	}

}
