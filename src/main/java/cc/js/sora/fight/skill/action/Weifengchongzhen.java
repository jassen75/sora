package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.CounterCondition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Weifengchongzhen extends Skill{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "威风冲阵";
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new CounterCondition() {

			public String getName()
			{
				return "Weifengchongzhen";
			}
			
			@Override
			public int getMaxCount() {
				// TODO Auto-generated method stub
				return 4;
			}

			public int getDefaultCount() {
				return 1;
			}

			@Override
			public boolean defaultValid() {
				return true;
			}

			@Override
			public String getDesc() {
				return "周围2圈有敌人,最高叠加4次";
			}
		};
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return getEffects(1);
	}
	
	@Override
	public List<Effect> getEffects(int count) {
		return Lists.newArrayList(new Enhance(BuffType.CriticalDamageInc, count*8, Scope.All));
	}

}
