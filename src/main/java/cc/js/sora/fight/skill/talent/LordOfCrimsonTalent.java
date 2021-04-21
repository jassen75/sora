package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class LordOfCrimsonTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "红月之王天赋";
	}

	public int getSkillType() {
		return 6;
	}
	
	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 15, Scope.All));
	}

}
