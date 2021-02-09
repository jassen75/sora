package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;

public class ZalrahdaTalent2 extends Skill {

	@Override
	public long getId() {
		return Skill.ZalrahdaTalent2;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "白毛捉迷藏";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new UserCondition() {

			@Override
			public String getDesc() {
				return "处于[危险范围]外";
			}

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "zhuomicang";
			}

			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return true;
			}};
	}

	@Override
	public List<Buff> getBuffs() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Buff(BuffType.CriticalProbInc, 30), new Buff(BuffType.CriticalDamageInc, 30), new Buff(BuffType.DamageDec, 30));
	}

	@Override
	public Scope getScope() {
		// TODO Auto-generated method stub
		return Scope.All;
	}

}
