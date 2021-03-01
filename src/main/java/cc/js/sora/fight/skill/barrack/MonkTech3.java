package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.FullHealthCondition;

public class MonkTech3 extends Skill {


	@Override
	public String getName() {
		return "僧侣科技：圣光护佑";
	}

	@Override
	public Condition getCondition() {
		return new FullHealthCondition();
	}

	@Override
	public List<Effect> getEffects()  {
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 30, Scope.Soldier));
	}

}
