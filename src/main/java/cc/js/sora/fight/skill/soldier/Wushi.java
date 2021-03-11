package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.DistanceCondition;
import cc.js.sora.fight.condition.DistanceGreaterThanCondition;
import cc.js.sora.fight.condition.health.FullHealthCondition;

public class Wushi extends Skill {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "武士技能";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new DistanceCondition(1);
	}
	
	public int getSkillType() {
		return 4;
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 20, Scope.Soldier));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "武士技能";
			}

			@Override
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new DistanceGreaterThanCondition(1);
			}
			
			public int getSkillType() {
				return 4;
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.CriticalProbInc, 20, Scope.Soldier));
			}
			
		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "武士技能";
			}

			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new Condition() {

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "有士兵存活";
					}

					@Override
					public boolean valid(FightInfo fightInfo, boolean isAttack) {
						// TODO Auto-generated method stub
						return fightInfo.getRole(isAttack).getSoldierLeftLife() > 0 ;
					}
					
				};
			}
			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Feature(Feature.ImmuneToMeleeDamageReduce, true, "免疫近战伤害减免",Scope.All, false));
			}
			
		});
	}

}
