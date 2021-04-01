package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class FlorentiaTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "弗洛朗蒂娅天赋";
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.Attack, 25, Scope.Soldier), new Enhance(BuffType.Physic, 25, Scope.Soldier),
				new Enhance(BuffType.Magic, 25, Scope.Soldier), new Enhance(BuffType.Tech, 25, Scope.Soldier));
	}

}
