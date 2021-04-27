package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Counter;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Qiutu  extends Skill{
	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "囚徒的齿轮技能";
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Counter(BuffType.Attack, 12, 3, Scope.All));
	}

}
