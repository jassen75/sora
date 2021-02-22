package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.DefLandCondition;

public class BirdTech3 extends Skill {

	@Override
	public long getId() {
		return Skill.BirdTech3;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "飞兵科技：特技飞行";
	}

	@Override
	public Condition getCondition() {
		return new DefLandCondition();
	}

	@Override
	public List<Effect> getEffects()  {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 20, Scope.Soldier));
	}

}
