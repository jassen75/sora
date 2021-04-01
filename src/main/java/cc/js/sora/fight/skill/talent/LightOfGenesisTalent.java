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
import cc.js.sora.fight.condition.EnemyHasDebuffCondition;
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.condition.health.FullHealthCondition;
import cc.js.sora.fight.condition.health.GreaterHealthCondition;
import cc.js.sora.fight.condition.health.LessHealthCondition;

public class LightOfGenesisTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "降生之光天赋";
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 15, Scope.All));
	}

	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "降生之光*裁决威光大心";
			}

			public Condition getCondition() {
				return new EnemyHasDebuffCondition();
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageDec, 10, Scope.All), new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
		},  new Skill() {
			
			@Override
			public String getDesc() {
				return "[降生之光天赋] 身上每有一个威光，智力提升5%";
			}

			@Override
			public String getName() {
				return "降生之光天赋";
			}

			public Condition getCondition() {
				return new CounterUserCondition() {
					
					public String getName() {
						return "Weiguang";
					}

					@Override
					public int getMaxCount() {
						// TODO Auto-generated method stub
						return 4;
					}

					@Override
					public int getDefaultCount() {
						// TODO Auto-generated method stub
						return 4;
					}

					@Override
					public boolean defaultValid() {
						// TODO Auto-generated method stub
						return true;
					}

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "身上拥有威光";
					}
				};
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList();
			}
			
			@Override
			public List<Effect> getEffects(int count) {
				if(count < 2)
				{
					return Lists.newArrayList(new Enhance(BuffType.Intel, 5*count, Scope.All));
				}
				if(count == 4)
				{
					return Lists.newArrayList(new Enhance(BuffType.Intel, 5*count, Scope.All), new Enhance(BuffType.Range, 2, Scope.All));
				}
				return Lists.newArrayList(new Enhance(BuffType.Intel, 5*count, Scope.All), new Enhance(BuffType.Range, 1, Scope.All));

			}
				
			
		}, new Skill() {

			public Condition getCondition() {
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
						return new FullHealthCondition().valid(fightInfo, isAttack);
					}
					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "身上有圣光之拥buff";
					}
					
				};
			}
			
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "圣光之拥";
			}

			
			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageDec, 25, Scope.All));
			}
			
		});
	}

}
