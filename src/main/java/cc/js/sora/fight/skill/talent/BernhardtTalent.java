package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Debuff;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.DistanceCondition;
import cc.js.sora.fight.condition.DistanceLessEqualCondition;
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.skill.action.Zhaojia;

public class BernhardtTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "巴恩哈特天赋";
	}

	public int getBattleType() {
		return 0;
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Debuff("BernhardtTalent", Lists.newArrayList(
				new Enhance(BuffType.Attack, -15, Scope.All), new Enhance(BuffType.Physic, -15, Scope.All))));
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new DistanceLessEqualCondition(2);
	}

	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "巴恩哈特*皇帝大心";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageDec, 10, Scope.All));
			}

			public int getSkillType() {
				return 4;
			}

		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "巴恩哈特*皇帝大心";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
			}

			public int getSkillType() {
				return 6;
			}

		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "巴恩哈特天赋";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 20, Scope.All));
			}

			public int getSkillType() {
				return 3;
			}

		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "铁血的野望";
			}

			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new UserCondition() {

					public String getName() {
						return "BernhardtSuper";
					}

					@Override
					public boolean defaultValid() {
						// TODO Auto-generated method stub
						return true;
					}

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "使用铁血的野望";
					}

				};
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 15, Scope.All));
			}

		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "霸气效果";
			}

			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new Condition() {

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "近战且敌人不免疫弱化";
					}

					@Override
					public boolean valid(FightInfo fightInfo, boolean isAttack) {
						// TODO Auto-generated method stub
						return new DistanceCondition(1).valid(fightInfo, isAttack) && 
								!fightInfo.getEnemyRole(isAttack).getHeroPanel().getFeatures().containsKey("ImmuneToDebuff");
					}
					
				} ;
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Debuff(BuffType.Physic, -20));
			}

			public int getSkillType() {
				return 3;
			}

			public int getBattleType() {
				return 1000;
			}

		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "铁血的野望*招架";
			}

			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new UserCondition() {

					public String getName() {
						return "BernhardtSuper";
					}

					@Override
					public boolean defaultValid() {
						// TODO Auto-generated method stub
						return true;
					}
					
					public boolean needCheck()
					{
						return true;
					}
					
					public boolean check(FightInfo fightInfo, boolean isAttack) {
						return new UserCondition() {
							
							public String getName() {
								return "Zhaojia";
							}

							@Override
							public boolean defaultValid() {
								// TODO Auto-generated method stub
								return false;
							}

							@Override
							public String getDesc() {
								// TODO Auto-generated method stub
								return "装备招架";
							}
							
						}.valid(fightInfo, isAttack);
					}
					

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "使用铁血的野望";
					}

				};
			}
			
			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.PhysicDamageDec, 25, Scope.All));
			}
			
		}, new Zhaojia());
	}
}
