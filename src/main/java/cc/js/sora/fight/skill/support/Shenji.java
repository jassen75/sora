package cc.js.sora.fight.skill.support;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;

public class Shenji extends Skill {

	public long getId() {
		return Skill.Shenji;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "神迹";
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
				return "神迹效果";
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
		return Lists.newArrayList(new Buff(BuffType.DamageDec, 15), 
				new Buff(BuffType.DamageInc, 15));
	}
	
	@Override
	public int getBattleType()
	{
		return 0;
	}

}
