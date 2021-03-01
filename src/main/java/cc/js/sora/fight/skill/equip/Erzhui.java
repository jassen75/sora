package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Counter;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Erzhui extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "星之耳坠技能";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Counter(BuffType.Physic, 30, 6, Scope.All),
				new Counter(BuffType.Physic, 30, 7, Scope.All));
	}

// bug?	
//	public int getSkillType()
//	{
//		return 3;
//	}
	
	

}
