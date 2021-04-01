package cc.js.sora.fight.skill.heart;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.LessHealthCondition;
import cc.js.sora.fight.skill.talent.LandiusTalent;

public class LandiusDayuanshuai extends Skill {


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "兰迪乌斯*大元帅大心";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All), new Enhance(BuffType.DamageDec, 10, Scope.All));
	}

	public int getSkillType() {
		return 3;
	}
	

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new LessHealthCondition(70);
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new LandiusTalent());
	}

}
