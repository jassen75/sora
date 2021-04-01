package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.NoCondition;

public class Yanshi extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "岩石巨人技能";
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new Condition() {

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "士兵生命低于70%";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				// TODO Auto-generated method stub
				return fightInfo.getRole(isAttack).getSoldierLifePercent()<0.7;
			}
			
		};
	}


	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 30, Scope.Soldier));
	}

}
