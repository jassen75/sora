package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.SoldierGreaterHealthCondition;

public class Shenguan extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "神官";
	}

	@Override
	public Condition getCondition() {
		return new SoldierGreaterHealthCondition(80);
	}
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Physic, 30, Scope.Soldier));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "神官技能";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.Medical, 30, Scope.Soldier));
			}
			
		});
	}

}
