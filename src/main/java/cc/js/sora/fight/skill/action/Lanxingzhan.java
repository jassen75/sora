package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;

public class Lanxingzhan  extends Skill {
	
	public Condition getCondition() {
		return new UserCondition() {

			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "队友人数大于等于5";
			}
			
		};
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "岚星斩";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 20, Scope.All), new Enhance(BuffType.Intel, 10, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "岚星斩";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.Range, 1, Scope.Soldier));
			}
			
		});
	}
	
}
