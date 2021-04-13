package cc.js.sora.fight.skill.support;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;

public class Bihuzhijian extends Skill {
	
	public long getId() {
		return Skill.Bihuzhijian;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "庇护之剑";
	}
	
	@Override
	public Condition getCondition() {
		return new UserCondition() {

			@Override
			public boolean getSupport()
			{
				return true;
			}
			
			@Override
			public String getDesc() {
				return "庇护之剑效果";
			}

			@Override
			public boolean defaultValid() {
				return false;
			}
		};
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 10, Scope.All), new Enhance(BuffType.Physic, 10, Scope.All),
				new Enhance(BuffType.Magic, 10, Scope.All), new Enhance(BuffType.Tech, 10, Scope.All));
	}

}
