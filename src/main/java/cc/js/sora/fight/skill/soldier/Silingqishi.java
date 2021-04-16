package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Counter;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Silingqishi extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "死灵骑士";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Counter(BuffType.Attack, 30, 1, Scope.Soldier),
				new Counter(BuffType.Attack, 30, 2, Scope.Soldier),
				new Counter(BuffType.Attack, 30, 3, Scope.Soldier),
				new Counter(BuffType.Attack, 30, 4, Scope.Soldier),
				new Counter(BuffType.Attack, 30, 5, Scope.Soldier),
				new Counter(BuffType.Attack, 30, 6, Scope.Soldier),
				new Counter(BuffType.Attack, 30, 7, Scope.Soldier),
				new Counter(BuffType.Attack, 30, 8, Scope.Soldier));
	}

}
