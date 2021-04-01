package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Counter;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Liefengzhiji extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "烈风直击";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Counter(BuffType.Attack, 30, 5, Scope.All));
	}


}
