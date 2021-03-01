package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.NoCondition;

public class Jixieqishi extends Skill {

	public long getId() {
		return Skill.Jixieqishi;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "机械骑士技能";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new NoCondition();
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 30, Scope.Soldier));
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
				return "机械骑士技能";
			}

			@Override
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new NoCondition();
			}
			
			public int getSkillType()
			{
				return 5;
			}

			@Override
			public List<Effect> getEffects() {
				return Lists.newArrayList(new Enhance(BuffType.Physic, 30, Scope.Soldier));
			}});
	}
	

}
