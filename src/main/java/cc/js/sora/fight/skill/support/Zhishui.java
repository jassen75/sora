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
import cc.js.sora.fight.condition.DistanceGreaterThanCondition;
import cc.js.sora.fight.condition.UserCondition;

public class Zhishui extends Skill {

	public long getId() {
		return Skill.ZhishuiHalo;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "止水光环";
	}
	
	@Override
	public Condition getCondition() {
		return new UserCondition() {

			public String getName()
			{
				return "Zhishui";
			}
			
			@Override
			public boolean getSupport()
			{
				return true;
			}
			
			@Override
			public String getDesc() {
				return "止水光环效果";
			}

			@Override
			public boolean defaultValid() {
				return false;
			}
		};
	}


	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.CriticalProbDec, 30, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {
			public long getId() {
				return Skill.ZhishuiHalo;
			}

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "止水光环";
			}

			public Condition getCondition() {
				return new UserCondition() {
					
					public String getName()
					{
						return "Zhishui";
					}

					@Override
					public boolean getSupport()
					{
						return true;
					}
					
					@Override
					public String getDesc() {
						return "止水光环效果";
					}

					@Override
					public boolean defaultValid() {
						return false;
					}
					
					public boolean needCheck()
					{
						return true;
					}
					
					public boolean check(FightInfo fightInfo, boolean isAttack) {
						return new DistanceGreaterThanCondition(1).valid(fightInfo, isAttack);
					}
				};
			}
			
			public int getSkillType() {
				return 2;
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageDec, 15, Scope.All));
			}

		});
	}
	

}
