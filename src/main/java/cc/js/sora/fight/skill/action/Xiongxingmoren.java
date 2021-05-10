package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Xiongxingmoren extends Skill  {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "凶星魔刃";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.CriticalProbInc, 25, Scope.All));
	}

}
