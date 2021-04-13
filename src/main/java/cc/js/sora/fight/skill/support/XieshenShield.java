package cc.js.sora.fight.skill.support;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;

public class XieshenShield extends Skill  {
	
	public long getId() {
		return Skill.XieshenShield;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "邪神盾";
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
				return "暗壁效果";
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
		return Lists.newArrayList(new Feature(Feature.XieshenShield, true, "暗壁效果，20%生命", Scope.All, false));
	}
	
	@Override
	public int getBattleType()
	{
		return 0;
	}

}
