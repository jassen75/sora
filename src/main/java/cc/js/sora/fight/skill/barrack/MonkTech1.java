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

public class MonkTech1 extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "僧侣科技：对魔特训";
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Counter(BuffType.Attack, 30, 9, Scope.Soldier, true),
				new Counter(BuffType.Physic, 30, 9, Scope.Soldier, true));
	}
	
	@Override
	public Condition getCondition() {
		return new Condition() {

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "对方士兵是魔物";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				// TODO Auto-generated method stub
				return fightInfo.getEnemyRole(isAttack).getSoldier().getType() == 9;
						//|| fightInfo.getEnemyRole(isAttack).getHero().getType() == 9;
			}
		};
	}
}
