package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Counter;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Bingqiang extends Skill {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "冰冻";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Counter(BuffType.Intel, 30, 2, Scope.All));
	}


}
