package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class ClarettZhuanshu extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "克拉蕾特专属";
	}

	public int getSkillType() {
		return 5;
	}
	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 20, Scope.All), new Enhance(BuffType.DamageDec, 20, Scope.All));
	}

}
