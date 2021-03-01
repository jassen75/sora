package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Land;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.LandCondition;
import cc.js.sora.fight.condition.SpecialLandCondition;

public class SailorTech4  extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "水兵科技：水灵加护";
	}

	@Override
	public Condition getCondition() {
		return new SpecialLandCondition(Land.Water);
	}

	@Override
	public List<Effect> getEffects()  {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack,20, Scope.Soldier), new Enhance(BuffType.Magic, 20, Scope.Soldier));
	}

}
