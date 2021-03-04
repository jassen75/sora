package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Jiamiannvpu extends Skill {

	public long getId() {
		return Skill.Jiamiannvpu;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "假面女仆技能";
	}

	public void process(FightInfo fight, boolean isAttack) {
		double result = Math.ceil(fight.getRole(isAttack).getSoldierLeftLife() * 0.95);
		fight.getRole(isAttack).setSoldierLeftLife(Double.valueOf(result).intValue());
	}

	public int getSkillType() {
		return 3;
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 45, Scope.Soldier),
				new Enhance(BuffType.Physic, 45, Scope.Soldier), new Enhance(BuffType.Magic, 45, Scope.Soldier),
				new Feature("FixDamageToSelf", 5, "士兵损失5%血量", Scope.Soldier));
	}

}
