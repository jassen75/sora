package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Counter;
import cc.js.sora.fight.Debuff;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Bujiechongji extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "不洁冲击";
	}
	
	public int getBattleType() {
		return 1;
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Debuff(BuffType.Attack, -30), new Debuff(BuffType.Intel, -30),
				new Debuff(BuffType.Physic, -30), new Debuff(BuffType.Magic, -30), new Counter(BuffType.Intel, 30, 10, Scope.All));
	}

}
