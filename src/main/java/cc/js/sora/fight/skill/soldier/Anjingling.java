package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Anjingling extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "暗精灵射手";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 45, Scope.Soldier), new Enhance(BuffType.Physic, 45, Scope.Soldier));
	}
	

}
