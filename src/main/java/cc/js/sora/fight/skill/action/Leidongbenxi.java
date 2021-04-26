package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.CounterUserCondition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Leidongbenxi extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "雷动奔袭";
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new CounterUserCondition() {

			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "不受士气低落影响，天赋每加1点移动";
			}

			@Override
			public int getMaxCount() {
				// TODO Auto-generated method stub
				return 4;
			}

			@Override
			public int getDefaultCount() {
				// TODO Auto-generated method stub
				return 2;
			}
			
		};
	}

	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return getEffects(2);
	}
	
	@Override
	public List<Effect> getEffects(int count) {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 5*count, Scope.All));
	}


}
