package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Counter;
import cc.js.sora.fight.CounterUserCondition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Feicuipofeng extends Skill{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "翡翠破弓";
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new CounterUserCondition() {

			public String getName()
			{
				return "Feicuipofeng";
			}
			
			@Override
			public int getMaxCount() {
				// TODO Auto-generated method stub
				return 2;
			}

			public int getDefaultCount() {
				return 2;
			}

			@Override
			public boolean defaultValid() {
				return true;
			}

			@Override
			public String getDesc() {
				return "转换敌方buff为debuff";
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
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, count*20, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "翡翠破弓";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Counter(BuffType.Attack, 30, 5, Scope.All));
			}
			
		});
	}

}
