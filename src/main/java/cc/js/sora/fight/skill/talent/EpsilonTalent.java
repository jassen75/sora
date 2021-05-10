package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.CounterUserCondition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.HasSuperCondition;

public class EpsilonTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "伊普西龙天赋";
	}

	public int getSkillType() {
		return 3;
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.CriticalDamageInc, 25, Scope.All),
				new Enhance(BuffType.CriticalProbInc, 25, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "伊普西龙天赋";
			}

			public int getSkillType() {
				return 4;
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.Range, 99, Scope.Soldier));
			}
			
		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "伊普西龙天赋";
			}
			
			public int getSkillType() {
				return 4;
			}

			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new HasSuperCondition() {
					
					public boolean needCheck()
					{
						return true;
					}
					
					public boolean check(FightInfo fightInfo, boolean isAttack) {
						return !fightInfo.getRole(isAttack).getAction().isSimpleAttack();
					}			
				};
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.Range, 2, Scope.Hero));
			}
			
		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "凶星魔刃";
			}

			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new CounterUserCondition() {

					public String getName()
					{
						return "Epsilon3C";
					}
					
					@Override
					public int getMaxCount() {
						// TODO Auto-generated method stub
						return 4;
					}

					public int getDefaultCount() {
						return 0;
					}

					@Override
					public boolean defaultValid() {
						return true;
					}

					@Override
					public String getDesc() {
						return "全场每有一个部队死亡或击杀部队";
					}
				};
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return getEffects(0);
			}

			@Override
			public List<Effect> getEffects(int count) {
				return Lists.newArrayList(new Enhance(BuffType.Attack, count*5, Scope.All));
			}
		});
	}


}
