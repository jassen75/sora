package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.NoCondition;
import cc.js.sora.fight.condition.UserCondition;

public class Zhaojia extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "招架";
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new UserCondition() {

			public String getName() {
				return "Zhaojia";
			}

			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "装备技能招架";
			}
			
		};
	}
	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Feature(Feature.AddAttackToPhysic, 7, "攻击的7%增加到防御", Scope.Hero, false));
	}

}
