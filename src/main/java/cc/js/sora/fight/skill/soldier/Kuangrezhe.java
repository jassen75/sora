package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.DefaultCounterUserCondition;

public class Kuangrezhe extends Skill {

	public long getId() {
		return Skill.Kuangrezhe;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "狂热者技能";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new DefaultCounterUserCondition(3, 3, "拥有强化效果");
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 15, Scope.Soldier),
				new Enhance(BuffType.Physic, 15, Scope.Soldier), new Enhance(BuffType.Magic, 15, Scope.Soldier));
	}

}
