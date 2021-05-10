package cc.js.sora.fight.skill.support;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.GroupedUserCondition;

public class EpsilonSuper extends Skill {
	public long getId() {
		return Skill.EpsilonSuper;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "伊普西龙超绝";
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(
				new Buff("EpsilonSuper", Lists.newArrayList(new Enhance(BuffType.CriticalDamageInc, 12, Scope.All),
						new Enhance(BuffType.CriticalProbInc, 12, Scope.All))));
	}
	
	public int getSkillType() {
		return 3;
	}
	
	public boolean isSupportSkill()
	{
		return true;
	}

	// 0 effect 1 pre battle 2 battle 3 post battle 4
	public int getBattleType() {
		return 0;
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new GroupedUserCondition() {
			
			@Override
			public boolean getSupport()
			{
				return true;
			}
			
			public String getName() 
			{
				return "EpsilonSuper";
			}
			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "流星直击队友";
			}

			@Override
			public String getGroupName() {
				// TODO Auto-generated method stub
				return "SuperBuff";
			}

		};
	}

}
