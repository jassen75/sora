package cc.js.sora.fight.skill;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;

public class SuperBuff extends Skill {

	@Override
	public long getId() {
		return Skill.SuperBuff;
	}

	@Override
	public String getName() {
		return "超绝强化";
	}

	@Override
	public Condition getCondition() {
		return new UserCondition() {

			@Override
			public String getDesc() {
				return "拥有超绝强化效果";
			}

			@Override
			public boolean defaultValid() {
				return true;
			}};
	}

	@Override
	public List<Buff> getBuffs() {
		return Lists.newArrayList(new Buff(BuffType.Attack, 20, true), new Buff(BuffType.PhysicDef,20, true), new Buff(BuffType.MagicDef, 30, true));
	}

	@Override
	public Scope getScope() {
		return Scope.All;
	}

}
