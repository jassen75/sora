package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.LessHealthCondition;

public class SorceressTech4 extends Skill {

	@Override
	public long getId() {
		return Skill.SorceressTech4;
	}

	@Override
	public String getName() {
		return "法师科技：聚精会神";
	}

	@Override
	public Condition getCondition() {
		return new LessHealthCondition(90);
	}

	@Override
	public List<Buff> getBuffs() {
		return Lists.newArrayList(new Buff(BuffType.DamageInc, 30));
	}

	@Override
	public Scope getScope() {
		return Scope.Soldier;
	}

}
