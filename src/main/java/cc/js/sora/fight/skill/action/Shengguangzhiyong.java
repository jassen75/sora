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
		return Lists.newArrayList(new Counter(BuffType.Intel, 20, 1, Scope.All),
				new Counter(BuffType.Intel, 20, 2, Scope.All), new Counter(BuffType.Intel, 20, 3, Scope.All),
				new Counter(BuffType.Intel, 20, 4, Scope.All), new Counter(BuffType.Intel, 20, 5, Scope.All),
				new Counter(BuffType.Intel, 20, 6, Scope.All), new Counter(BuffType.Intel, 20, 7, Scope.All),
				new Counter(BuffType.Intel, 20, 8, Scope.All), new Counter(BuffType.Intel, 20, 9, Scope.All));
	}

}
