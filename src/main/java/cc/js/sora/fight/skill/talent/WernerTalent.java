package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.CounterUserCondition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.GroupedUserCondition;
import cc.js.sora.fight.condition.NoCondition;

public class WernerTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "沃尔纳天赋";
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList();
	}
	
	private Condition Jisu = new GroupedUserCondition() {
		@Override
		public String getName() {
			return "UseJisu";
		}

		@Override
		public boolean defaultValid() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public String getDesc() {
			// TODO Auto-generated method stub
			return "机动变形*疾速形态";
		}

		@Override
		public String getGroupName() {
			// TODO Auto-generated method stub
			return "Jidongbianxing";
		}
	} ;

	private Condition Fangyu = new GroupedUserCondition() {
		@Override
		public String getName() {
			return "UseFangyu";
		}

		@Override
		public boolean defaultValid() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getDesc() {
			// TODO Auto-generated method stub
			return "机动变形*防御形态";
		}

		@Override
		public String getGroupName() {
			// TODO Auto-generated method stub
			return "Jidongbianxing";
		}
	};
	
	private Condition Qiangxi =  new GroupedUserCondition() {
		@Override
		public String getName() {
			return "UseQiangxi";
		}

		@Override
		public boolean defaultValid() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getDesc() {
			// TODO Auto-generated method stub
			return "机动变形*强袭形态";
		}

		@Override
		public String getGroupName() {
			// TODO Auto-generated method stub
			return "Jidongbianxing";
		}
	};
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "沃尔纳*机械统帅大心";
			}

			public int getSkillType() {
				return 7;
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
				return "沃尔纳*机械统帅大心";
			}

			public Condition getCondition() {
				return new Condition() {

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "受到物理伤害";
					}

					@Override
					public boolean valid(FightInfo fightInfo, boolean isAttack) {
						// TODO Auto-generated method stub
						return fightInfo.getEnemyRole(isAttack).getHeroPanel().getAttackType()==1;
					}
					
				};
			}
			
			public int getSkillType() {
				return 5;
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
		},new Skill() {
			
			@Override
			public String getName() {
				return "沃尔纳变身";
			}

			public Condition getCondition() {
				return new CounterUserCondition() {
					
					public String getName() {
						return "Bianshen";
					}

					@Override
					public int getMaxCount() {
						// TODO Auto-generated method stub
						return 3;
					}

					@Override
					public int getDefaultCount() {
						// TODO Auto-generated method stub
						return 1;
					}

					@Override
					public boolean defaultValid() {
						// TODO Auto-generated method stub
						return true;
					}

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "变身一次，全属性+5%";
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
				return Lists.newArrayList(new Enhance(BuffType.Attack, count*5, Scope.All), new Enhance(BuffType.Physic, count*5, Scope.All),
						new Enhance(BuffType.Magic, count*5, Scope.All), new Enhance(BuffType.Tech, count*5, Scope.All));

			}
				
			
		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "机动变形*疾速形态";
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return Jisu;
			}

			@Override
			public List<Effect> getEffects() {
				return Lists.newArrayList(new Feature(Feature.FirstAttack, true, "先于敌人攻击", Scope.All, false));
			}
			
		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "机动变形*防御形态";
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return Fangyu;
			}

			@Override
			public List<Effect> getEffects() {
				return Lists.newArrayList(new Enhance(BuffType.DamageDec, 15, Scope.All));
			}
			
		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "机动变形*防御形态";
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return Fangyu;
			}
			
			public int getSkillType() {
				return 5;
			}

			@Override
			public List<Effect> getEffects() {
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 20, Scope.All));
			}
			
		},  new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "机动变形*强袭形态";
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return Qiangxi;
			}
			
			public int getSkillType() {
				return 4;
			}

			@Override
			public List<Effect> getEffects() {
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 15, Scope.All));
			}
			
		});
	}

}
