package cc.js.sora.fight.skill.heart;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.HealthLessThanEnemy;

public class MarielCiSheng  extends Skill {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "玛丽埃尔*赐圣僧侣大心";
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new HealthLessThanEnemy();
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 10, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "玛丽埃尔*赐圣僧侣大心";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.Medical, 10, Scope.Hero));
			}
			
		}, new cc.js.sora.fight.skill.talent.MarielTalent());
	}

}
