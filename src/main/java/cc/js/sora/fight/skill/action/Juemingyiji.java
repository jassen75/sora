package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Juemingyiji extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "绝命一击";
	}

	public Condition getCondition()
	{
		return new Condition() {

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "没有士兵";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				// TODO Auto-generated method stub
				return fightInfo.getRole(isAttack).getSoldierLeftLife() == 0;
			}};
	}
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Feature("DirectToHero", true, "直击本体", Scope.Hero));
	}

}
