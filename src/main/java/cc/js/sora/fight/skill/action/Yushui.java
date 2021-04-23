package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Land;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;

public class Yushui extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "驭水";
	}

	public Condition getCondition() {
		return new UserCondition() {

			@Override
			public boolean defaultValid() {
				return true;
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "使用驭水";
			}
		};
	}
	public int getBattleType() {
		return 0;
	}
	
	public void process(FightInfo fightInfo, boolean isAttack) {
		fightInfo.getRole(isAttack).setLand(Land.Water);
	}
	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Feature(Feature.LandAsWater, true, "地形视为水中", Scope.All, false));
	}
}
