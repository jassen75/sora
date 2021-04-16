package cc.js.sora.fight.skill.enhance;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.EnemyHealthGreaterCondition;

public class LiuxingEnhance  extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "流星附魔";
	}

	public int getSkillType() {
		return 4;
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new EnemyHealthGreaterCondition(60);
	}
	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 20, Scope.Hero));
	}

}
