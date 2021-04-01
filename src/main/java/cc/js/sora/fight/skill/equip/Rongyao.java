package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Rongyao extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "荣耀的传承";
	}
	
	public int getSkillType() {
		return 5;
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.PhysicDamageDec, 10, Scope.All), 
				new Feature(Feature.RangeAttackBack, 99, "N格远程反击", Scope.All, false));
	}

}
