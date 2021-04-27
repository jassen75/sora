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

public class RanfordHalo extends Skill {
	
	
	public long getId() {
		return Skill.RanfordHalo;
	}
	

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "兰芳特天赋";
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
				return "兰芳特天赋效果";
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
		return Lists.newArrayList(new Enhance(BuffType.Attack, 15, Scope.All), new Enhance(BuffType.Physic, 15, Scope.All),
				new Enhance(BuffType.Magic, 15, Scope.All), new Enhance(BuffType.Tech, 15, Scope.All));
	}
}
