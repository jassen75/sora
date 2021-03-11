package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Counter;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Shengguangzhiyong extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "圣光之拥";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Counter(BuffType.Intel, 30, 1, Scope.All),
				new Counter(BuffType.Intel, 30, 2, Scope.All), new Counter(BuffType.Intel, 30, 3, Scope.All),
				new Counter(BuffType.Intel, 30, 4, Scope.All), new Counter(BuffType.Intel, 30, 5, Scope.All),
				new Counter(BuffType.Intel, 30, 6, Scope.All), new Counter(BuffType.Intel, 30, 7, Scope.All),
				new Counter(BuffType.Intel, 30, 8, Scope.All), new Counter(BuffType.Intel, 30, 9, Scope.All));
	}

}
