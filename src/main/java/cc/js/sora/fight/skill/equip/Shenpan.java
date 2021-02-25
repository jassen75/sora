package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Counter;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Shenpan extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "审判魔符技能";
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Counter(BuffType.Attack, 12, 10, Scope.All));
	}

}
