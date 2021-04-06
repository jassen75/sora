package cc.js.sora.fight.skill.heart;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.GreaterHealthCondition;
import cc.js.sora.fight.skill.talent.FreyaTalent;

public class FreyaSP extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "芙蕾雅*蔷薇女王大心";
	}
	
	public int getSkillType() {
		return 3;
	}
	
	public Condition getCondition() {
		return new GreaterHealthCondition(80);
	}
	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 10, Scope.All), new Enhance(BuffType.DamageInc, 20, Scope.Soldier));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new FreyaTalent());
	}

}
