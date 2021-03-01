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

public class FootTech2 extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "步兵科技：压制战法";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new Condition(){

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "部队血量百分比高于或者等于对面";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				// TODO Auto-generated method stub
				return fightInfo.getRole(isAttack).getLifePercent() >= fightInfo.getEnemyRole(isAttack).getLifePercent();
			}};
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.Attack, 20, Scope.Soldier), new Enhance(BuffType.Physic, 20, Scope.Soldier));
	}

}
