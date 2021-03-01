package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.GreaterHealthCondition;

public class SailorTech1  extends Skill {

	@Override
	public String getName() {
		return "水兵科技：先制打击";
	}

	@Override
	public Condition getCondition() {
		return new GreaterHealthCondition(80);
	}

	@Override
	public List<Effect> getEffects()  {
		return Lists.newArrayList(new Enhance(BuffType.Attack, 20, Scope.Soldier), new Enhance(BuffType.Physic, 20, Scope.Soldier));
	}
}
