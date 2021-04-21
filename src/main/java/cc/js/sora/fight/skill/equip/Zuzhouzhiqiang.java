package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Zuzhouzhiqiang extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "诅咒之枪";
	}
	
	public int getSkillType() {
		return 4;
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Physic, 15, Scope.Hero), new Enhance(BuffType.Magic, 15, Scope.Hero));
	}

}
