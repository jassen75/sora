package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Shenyi extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "神翼护胫技能";
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.Physic, 10, Scope.Hero));
	}
	
	public int getSkillType()
	{
		return 5;
	}

}
