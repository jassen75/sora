package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.condition.health.HealthGreaterThanEnemy;

public class Yazhi extends Skill{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "压制";
	}

	public int getSkillType() {
		return 3;
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new UserCondition() {

			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "装备技能压制";
			}
			
			public boolean needCheck()
			{
				return true;
			}
			
			public boolean check(FightInfo fightInfo, boolean isAttack) {
				return new HealthGreaterThanEnemy().valid(fightInfo, isAttack);
			}
		};
	}
	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 12, Scope.All));
	}

}
