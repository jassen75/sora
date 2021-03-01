package cc.js.sora.fight.skill.soldier;

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

public class LobsterSkill extends Skill {

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return Skill.LongxiajushouSkill;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "龙虾巨兽技能";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new SpecialLandCondition(Land.Water);
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 50, Scope.Soldier));
	}


}
