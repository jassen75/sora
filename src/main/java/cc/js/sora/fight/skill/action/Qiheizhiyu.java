package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.CounterUserCondition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Qiheizhiyu extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "漆黑之欲";
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Feature(Feature.PhysicToAttack, 1.6, "防御的1.6倍视为攻击", Scope.Hero, false));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "漆黑之欲";
			}

			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new CounterUserCondition() {

					public String getName()
					{
						return "Qiheizhiyu";
					}
					
					@Override
					public int getMaxCount() {
						// TODO Auto-generated method stub
						return 3;
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
						return "驱散自身弱化效果";
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
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, count*10, Scope.All));
			}
			
		});
	}

}
