package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Land;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.LandCondition;
import cc.js.sora.fight.condition.SpecialLandCondition;

public class SailorTech3  extends Skill {

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return Skill.SailorTech3;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "水兵科技：水战强化";
	}

	@Override
	public Condition getCondition() {
		return new SpecialLandCondition(Land.Water);
	}

	@Override
	public List<Buff> getBuffs() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Buff(BuffType.DamageInc, 30));
	}

	@Override
	public Scope getScope() {
		// TODO Auto-generated method stub
		return Scope.Soldier;
	}
	
	//1 attack 2 defender 3 all
	public int getSkillType()
	{
		return 1;
	}

}
