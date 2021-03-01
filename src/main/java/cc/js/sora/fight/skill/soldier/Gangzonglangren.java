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

public class Gangzonglangren extends Skill{
	
	public long getId() {
		return Skill.Gangzonglangren;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "钢鬃狼人技能";
	}
	
	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new SoldierGreaterHealthCondition(70);
	}
	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 40, Scope.Soldier));
	}
	
	public int getSkillType()
	{
		return 4;
	}
	
	public List<Skill> childSkill()
	{
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
			    return "钢鬃狼人技能";
			}
			
			@Override
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new SoldierGreaterHealthCondition(70);
			}
			
			public int getSkillType()
			{
				return 5;
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.Physic, 40, Scope.Soldier));
			}});
	}

}
