package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;

public class BirdTech4 extends Skill  {

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return Skill.BirdTech4;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "飞兵科技：地空协同";
	}

	@Override
	public Condition getCondition() {
		return new UserCondition() {

			@Override
			public String getDesc() {
				return "混合部队";
			}

			@Override
			public String getName() {
				return "HybridForce";
			}

			@Override
			public boolean defaultValid() {
				return false;
			}};
	}

	@Override
	public List<Buff> getBuffs() {
		return Lists.newArrayList(new Buff(BuffType.MagicDef, 20), new Buff(BuffType.PhysicDef, 20));
	}

	@Override
	public Scope getScope() {
		return Scope.Soldier;
	}

}
