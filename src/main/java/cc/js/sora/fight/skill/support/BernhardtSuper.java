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

public class BernhardtSuper extends Skill {

	public long getId() {
		return Skill.BernhardtSuper;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "铁血的野望";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(
				new Buff("BernhardtSuper", Lists.newArrayList(new Enhance(BuffType.DamageInc, 18, Scope.All))));
	}

	public int getSkillType() {
		return 3;
	}
	
	@Override
	public int getBattleType()
	{
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
				return "BernhardtSuper";
			}
			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "帝国之辉队友";
			}

			@Override
			public String getGroupName() {
				// TODO Auto-generated method stub
				return "SuperBuff";
			}

		};
	}

}
