package cc.js.sora.fight.skill.support;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.GroupedUserCondition;
import cc.js.sora.fight.condition.NotSimpleAttack;

public class YuusukeSuper extends Skill {
	
	public long getId() {
		return Skill.YuusukeSuper;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "魔强一统，浦饭幽助超绝";
	}
	
	public int getSkillType() {
		return 4;
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new GroupedUserCondition() {
			
			@Override
			public boolean getSupport()
			{
				return true;
			}
			
			public String getName() 
			{
				return "YuusukeSuper";
			}
			
			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "时空枢纽队友";
			}

			@Override
			public String getGroupName() {
				// TODO Auto-generated method stub
				return "SuperBuff";
			}
			
			public boolean needCheck() {
				return true;
			}

			public boolean check(FightInfo fightInfo, boolean isAttack) {
				return new NotSimpleAttack().valid(fightInfo, isAttack);
				
			}

		};
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
	}

}
