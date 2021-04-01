package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class RozencielTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "罗泽希尔天赋";
	}

	public int getSkillType() {
		return 6;
	}
	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 25, Scope.All));
	}

}
