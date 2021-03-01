package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.GreaterHealthCondition;

public class Xuanfengyouqibing extends Skill{
	
	public long getId() {
		return Skill.Xuanfengyouqibing;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "旋风游骑兵技能";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new GreaterHealthCondition(50);
	}
	
	public int getSkillType() {
		return 5;
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 50, Scope.Soldier));
	}

}
