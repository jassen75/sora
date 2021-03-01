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

public class FootTech1 extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "步兵科技：对枪特训";
	}

	@Override
	public Condition getCondition() {
		return new Condition() {

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "对方部队是枪兵";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				// TODO Auto-generated method stub
				return fightInfo.getEnemyRole(isAttack).getSoldier().getType() == 2;
						//|| fightInfo.getEnemyRole(isAttack).getHero().getType() == 2;
			}
		};
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Counter(BuffType.Attack, 30, 2, Scope.All, true),
				new Counter(BuffType.Physic, 30, 2, Scope.Soldier, true));
	}

}
