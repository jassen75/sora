package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.NoCondition;
import cc.js.sora.fight.condition.UserCondition;

public class BloodSwordAction extends Skill {

	@Override
	public Condition getCondition() {
		return new UserCondition() {

			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "使用嗜血魔剑";
			}
		};
	}

	public void process(FightInfo fight, boolean isAttack) {
		fight.getRole(isAttack).setSoldierLeftLife(0);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "嗜血魔剑";
	}
	
	public int getBattleType() {
		return 0;
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(
				new Buff("Shixuemojian", Lists.newArrayList(new Enhance(BuffType.DamageInc, 30, Scope.All))),
				new Feature("UnImuFixDamageToSelf", 100, "献祭所有士兵", Scope.Soldier, true));
	}

}
