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
import cc.js.sora.fight.skill.action.Beici;

public class TsubameTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "燕天赋";
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new CounterUserCondition() {

			public String getName()
			{
				return "TsubameTalent";
			}
			
			@Override
			public int getMaxCount() {
				// TODO Auto-generated method stub
				return 3;
			}

			public int getDefaultCount() {
				return 3;
			}

			@Override
			public boolean defaultValid() {
				return true;
			}

			@Override
			public String getDesc() {
				return "周围3格每有1个敌人";
			}
		};
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return getEffects(3);
	}

	@Override
	public List<Effect> getEffects(int count) {
		return Lists.newArrayList(new Enhance(BuffType.Attack, count*8, Scope.All), new Enhance(BuffType.Tech, count*8, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Beici());
	}


}
