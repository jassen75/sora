package cc.js.sora.fight.skill.enhance;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class DashuEnhance  extends Skill{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "大树附魔";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Physic, 5, Scope.All),new Enhance(BuffType.Magic, 5, Scope.All));
	}

}
