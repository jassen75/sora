package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.FixSoldierCondition;
import cc.js.sora.fight.condition.UserCondition;

public class BirdTech4 extends Skill  {


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "飞兵科技：地空协同";
	}

	@Override
	public Condition getCondition() {
		return new FixSoldierCondition();
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.Magic, 20, Scope.Soldier), new Enhance(BuffType.Physic, 20, Scope.Soldier));
	}


}
