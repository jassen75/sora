package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
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
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.CriticalProbInc, 30, Scope.All),
				new Enhance(BuffType.CriticalDamageInc, 30, Scope.All), new Enhance(BuffType.DamageDec, 30, Scope.All));
	}

}
