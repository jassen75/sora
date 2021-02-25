package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Counter;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class BowTech5 extends Skill {

	@Override
	public String getName() {
		return "弓箭科技：密林游侠";
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Counter(BuffType.Attack, 30, 5, Scope.Soldier, true),
				new Counter(BuffType.Physic, 30, 5, Scope.Soldier, true));
	}
	
	@Override
	public Condition getCondition() {
		return new Condition() {

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "对方部队是鸟兵";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				// TODO Auto-generated method stub
				return fightInfo.getEnemyRole(isAttack).getSoldier().getType() == 5;
					//	|| fightInfo.getEnemyRole(isAttack).getHero().getType() == 5;
			}
		};
	}

}
