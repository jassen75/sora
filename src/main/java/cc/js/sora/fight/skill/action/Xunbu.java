package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Skill;

public class Xunbu extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "迅步";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Buff(BuffType.Tech, 30));
	}

}
