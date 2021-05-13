package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Debuff;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.NoCondition;
import cc.js.sora.fight.condition.NoFeatureCondition;
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.skill.action.Zaiyidong;

public class BrendaTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "布琳达天赋";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new Condition() {

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "部队血量百分比高于或者等于对面";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				// TODO Auto-generated method stub
				return fightInfo.getRole(isAttack).getLifePercent() >= fightInfo.getEnemyRole(isAttack)
						.getLifePercent();
			}
		};
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 30, Scope.All));
	}

	public List<Skill> childSkill() {
		return Lists.newArrayList(new Zaiyidong(), new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "风华绝代";
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new UserCondition() {

					@Override
					public boolean defaultValid() {
						// TODO Auto-generated method stub
						return true;
					}

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "主动使用风华绝代";
					}
					
				};
			}


			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
			
		},new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "风华绝代";
			}
			
			public int getBattleType() {
				return 1000;
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
						return "主动使用风华绝代，且触发破甲效果(50%)";
					}
					
					public boolean needCheck()
					{
						return true;
					}
					
					public boolean check(FightInfo fightInfo, boolean isAttack) {
						return new NoFeatureCondition(Feature.ImmuneToDebuff, "对面不免疫弱化").valid(fightInfo, isAttack) && 
								new NoFeatureCondition(Feature.ImmuneToADReduce, "对面不免疫攻防降低").valid(fightInfo, isAttack);
					}
					
				};
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Debuff(BuffType.Physic, -20));
			}
			
		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "风华绝代";
			}
			
			public int getBattleType() {
				return 1000;
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
						return new NoFeatureCondition(Feature.ImmuneToDebuff, "对面不免疫弱化").valid(fightInfo, isAttack) && 
								new NoFeatureCondition(Feature.ImmuneToADReduce, "对面不免疫攻防降低").valid(fightInfo, isAttack);
					}

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "主动使用风华绝代，且触发破攻效果(50%)";
					}
					
				};
			}


			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Debuff(BuffType.Attack, -20));
			}
			
		});
	}

}
