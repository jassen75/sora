package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.CounterCondition;

public class Qiangfengjuji extends Skill {

	public Condition getCondition() {
		return new CounterCondition() {

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "攻击距离";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public int getMaxCount() {
				// TODO Auto-generated method stub
				return 6;
			}

			@Override
			public int getDefaultCount() {
				// TODO Auto-generated method stub
				return 6;
			}

			@Override
			public int getCount(FightInfo fightInfo, boolean isAttack) {
				// TODO Auto-generated method stub
				return fightInfo.getDistance();
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
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, count*15, Scope.Hero));
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "强风狙击";
	}

}
