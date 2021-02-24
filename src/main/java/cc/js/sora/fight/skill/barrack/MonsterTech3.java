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
import cc.js.sora.fight.condition.health.FullHealthCondition;

public class MonsterTech3 extends Skill {

	@Override
	public String getName() {
		return "魔物科技：污秽铠甲";
	}

	@Override
	public Condition getCondition() {
		return new Condition() {

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "对方不是圣职或者魔物";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				// TODO Auto-generated method stub
				return fightInfo.getEnemyRole(isAttack).getSoldier().getType()!=2 & fightInfo.getEnemyRole(isAttack).getSoldier().getType()!=2;
			}};
	}

	@Override
	public List<Effect> getEffects()  {
		return Lists.newArrayList(new Enhance(BuffType.MagicDef, 20, Scope.Soldier), new Enhance(BuffType.PhysicDef, 20, Scope.Soldier));
	}

}
