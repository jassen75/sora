package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.CounterUserCondition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class KreugerTheWickedTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "邪神天赋";
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new CounterUserCondition() {

			public String getName()
			{
				return "KreugerTheWickedTalent";
			}
			
			@Override
			public int getMaxCount() {
				// TODO Auto-generated method stub
				return 6;
			}

			public int getDefaultCount() {
				return 6;
			}

			@Override
			public boolean defaultValid() {
				return true;
			}

			@Override
			public String getDesc() {
				return "全场每有一个debuff";
			}
		};
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return getEffects(6);
	}

	@Override
	public List<Effect> getEffects(int count) {
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, count*5, Scope.All));
	}

}
