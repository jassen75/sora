package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Counter;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Qumoshi extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "驱魔师";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Counter(BuffType.Attack, 30, 9, Scope.Soldier),
				new Counter(BuffType.Physic, 30, 1, Scope.Soldier),
				new Counter(BuffType.Physic, 30, 2, Scope.Soldier),
				new Counter(BuffType.Physic, 30, 3, Scope.Soldier),
				new Counter(BuffType.Physic, 30, 4, Scope.Soldier),
				new Counter(BuffType.Physic, 30, 5, Scope.Soldier),
				new Counter(BuffType.Physic, 30, 6, Scope.Soldier),
				new Counter(BuffType.Physic, 30, 7, Scope.Soldier),
				new Counter(BuffType.Physic, 30, 8, Scope.Soldier),
				new Counter(BuffType.Physic, 30, 10, Scope.Soldier)
				);
	}

}
