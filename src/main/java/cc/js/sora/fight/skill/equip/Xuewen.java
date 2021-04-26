package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.DistanceCondition;
import cc.js.sora.fight.condition.UserCondition;

public class Xuewen extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "血纹魔铠";
	}
	
	public int getSkillType() {
		return 5;
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new UserCondition() {

			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean needCheck()
			{
				return true;
			}
			
			public boolean check(FightInfo fightInfo, boolean isAttack) {
				return new DistanceCondition(1).valid(fightInfo, isAttack);
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "近战被攻击30%概率触发血纹";
			}
			
		};
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 30, Scope.All));
	}

}
