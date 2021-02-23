package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.NoDefLandConddition;

public class SpearTech4 extends Skill  {

	@Override
	public String getName() {
		return "枪兵科技：巩固防线";
	}

	@Override
	public Condition getCondition() {
		return new NoDefLandConddition();
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.MagicDef, 20, Scope.Soldier), new Enhance(BuffType.PhysicDef, 20, Scope.Soldier));
	}

}
