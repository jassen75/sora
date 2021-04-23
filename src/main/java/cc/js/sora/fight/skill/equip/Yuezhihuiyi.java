package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.NoCondition;
import cc.js.sora.fight.condition.UserCondition;

public class Yuezhihuiyi extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "月之回忆";
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new UserCondition() {

			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "触发天赋再动时";
			}
			
		};
	}
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 10, Scope.All), new Enhance(BuffType.DamageInc, 10, Scope.All));
	}

}
