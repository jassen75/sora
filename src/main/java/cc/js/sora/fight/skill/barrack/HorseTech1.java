package cc.js.sora.fight.skill.barrack;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class HorseTech1 extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "骑兵科技：对步特训";
	}

	@Override
	public Condition getCondition() {
		return new Condition() {

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "对方是步兵";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				// TODO Auto-generated method stub
				return fightInfo.getEnemyRole(isAttack).getSoldier().getType()==2;
			}};
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.PhysicDef, 30, Scope.Soldier));
	}

}
