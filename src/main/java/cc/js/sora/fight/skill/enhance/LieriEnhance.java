package cc.js.sora.fight.skill.enhance;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class LieriEnhance extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "烈日附魔";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.CriticalDamageInc, 25, Scope.All));
	}

}
