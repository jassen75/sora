package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.CounterUserCondition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.DistanceCondition;
import cc.js.sora.fight.condition.health.GreaterHealthCondition;
import cc.js.sora.fight.skill.action.Qishijingshen;

public class RozaliaTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "罗莎莉亚天赋";
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.Attack, 20, Scope.All), new Enhance(BuffType.Physic, 20, Scope.All));
	}
	
	public int getSkillType()
	{
		return 3;
	}
	
	public Condition getCondition() {
		return new GreaterHealthCondition(50);
	}

	public List<Skill> childSkill()
	{
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "罗莎莉亚*银骑统帅大心";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageDec, 10, Scope.All));
			}
			
			public int getSkillType()
			{
				return 4;
			}
			
		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "罗莎莉亚*银骑统帅大心";
			}

			public Condition getCondition() {
				return new DistanceCondition(1);
			}
			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
			
			public int getSkillType()
			{
				return 5;
			}
			
		}, new Skill() {

			public Condition getCondition() {
				return new CounterUserCondition() {
	
					public String getName()
					{
						return "RozaliaTalent";
					}

					@Override
					public int getMaxCount() {
						// TODO Auto-generated method stub
						return 3;
					}

					@Override
					public int getDefaultCount() {
						// TODO Auto-generated method stub
						return 3;
					}

					@Override
					public boolean defaultValid() {
						// TODO Auto-generated method stub
						return true;
					}

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "进入战斗前或者周围2圈有敌人";
					}
				};
			}
			
			@Override
			public List<Effect> getEffects(int count) {
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, count*5, Scope.All), new Enhance(BuffType.DamageDec, count*5, Scope.All));
			}

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "罗莎莉亚天赋";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return getEffects(3);
			}}, new Qishijingshen());
	}

}
