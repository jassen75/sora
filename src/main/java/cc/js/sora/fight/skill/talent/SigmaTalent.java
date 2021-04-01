package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.GroupedUserCondition;
import cc.js.sora.fight.condition.NoCondition;
import cc.js.sora.fight.condition.UserCondition;

public class SigmaTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "西格玛天赋";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 15, Scope.All));
	}
	
	public int getSkillType() {
		return 3;
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "西格玛天赋";
			}

			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new GroupedUserCondition() {

					@Override
					public boolean defaultValid() {
						// TODO Auto-generated method stub
						return true;
					}

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "有弑影效果";
					}
					
					@Override
					public String getGroupName() {
						// TODO Auto-generated method stub
						return "Shiying";
					}
					
				};
			}
			
			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.Range, 2, Scope.All));
			}}, new Skill() {

				@Override
				public String getName() {
					// TODO Auto-generated method stub
					return "西格玛*超越者之刃大心";
				}
				
				public int getSkillType() {
					return 4;
				}

				@Override
				public List<Effect> getEffects() {
					// TODO Auto-generated method stub
					return Lists.newArrayList(new Enhance(BuffType.DamageDec, 10, Scope.All));
				}
				
			}, new Skill() {

				@Override
				public String getName() {
					// TODO Auto-generated method stub
					return "西格玛*超越者之刃大心";
				}

				public int getSkillType() {
					return 5;
				}
				
				@Override
				public List<Effect> getEffects() {
					// TODO Auto-generated method stub
					return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
				}
				
			}, new Skill() {

				@Override
				public String getName() {
					// TODO Auto-generated method stub
					return "西格玛天赋";
				}

				public Condition getCondition() {
					// TODO Auto-generated method stub
					return new GroupedUserCondition() {

						@Override
						public boolean defaultValid() {
							// TODO Auto-generated method stub
							return false;
						}

						@Override
						public String getDesc() {
							// TODO Auto-generated method stub
							return "攻击完敌人(失去弑影效果)";
						}

						@Override
						public String getGroupName() {
							// TODO Auto-generated method stub
							return "Shiying";
						}
						
					};
				}
				
				public int getBattleType() {
					return 0;
				}
				
				@Override
				public List<Effect> getEffects() {
					// TODO Auto-generated method stub
					return Lists.newArrayList(new Enhance(BuffType.DamageDec, 30, Scope.All));
				}
				
			},new Skill() {

				@Override
				public String getName() {
					// TODO Auto-generated method stub
					return "西格玛天赋";
				}

				public Condition getCondition() {
					// TODO Auto-generated method stub
					return new UserCondition() {

						public String getName()
						{
							return "AttackYouxiayinji";
						}
						
						@Override
						public boolean defaultValid() {
							// TODO Auto-generated method stub
							return false;
						}

						@Override
						public String getDesc() {
							// TODO Auto-generated method stub
							return "攻击游侠印记的敌人";
						}
					};
				}
				
				public int getBattleType() {
					return 0;
				}
				
				@Override
				public List<Effect> getEffects() {
					// TODO Auto-generated method stub
					return Lists.newArrayList(new Enhance(BuffType.DamageInc, 15, Scope.All), new Feature(Feature.ImmuneToMeleeDamageReduce, true, "无视近战伤害减免", Scope.All, false));
				}
				
			}, new Skill() {

				@Override
				public String getName() {
					// TODO Auto-generated method stub
					return "弑影对决";
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
							return "弑影对决以后";
						}
						
					};
				}
				
				public int getBattleType() {
					return 0;
				}
				
				@Override
				public List<Effect> getEffects() {
					// TODO Auto-generated method stub
					return Lists.newArrayList(new Enhance(BuffType.DamageInc, 50, Scope.All));
				}
				
			});
	}

}
