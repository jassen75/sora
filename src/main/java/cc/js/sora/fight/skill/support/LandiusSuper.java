package cc.js.sora.fight.skill.support;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Counter;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.GroupedUserCondition;

public class LandiusSuper extends Skill {

	public long getId() {
		return Skill.LandiusSuper;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "兰迪乌丝超绝";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Counter(BuffType.Attack, 8, 0, Scope.All));
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new GroupedUserCondition() {

			@Override
			public boolean getSupport() {
				return true;
			}

			public String getName() {
				return "LandiusSuper";
			}

			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean needCheck() {
				return true;
			}

			public boolean check(FightInfo fightInfo, boolean isAttack) {
				return !fightInfo.hasCounterRelation();
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "传说彼端队友,且不存在克制关系";
			}

			@Override
			public String getGroupName() {
				// TODO Auto-generated method stub
				return "SuperBuff";
			}

		};
	}

}
