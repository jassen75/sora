package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.usercondition.FriendDead;

public class Mimier extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "弥米尔的战锤";
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new FriendDead();
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Buff(BuffType.DamageInc, 20));
	}

}
