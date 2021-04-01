package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Shijieshu extends Skill {
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "世界树的嫩枝";
	}
	
	public int getSkillType() {
		return 3;
	}
	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.IgnoreDef,  15, Scope.Hero));
	}
}
