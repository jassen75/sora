package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Counter;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Shengdianqishi extends Skill{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "圣殿骑士";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Magic, 45, Scope.Soldier), new Counter(BuffType.Attack, 45, 9, Scope.Soldier),
				new Counter(BuffType.Physic, 45, 9, Scope.Soldier));
	}

}
