package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.FightRole;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.CounterCondition;
import cc.js.sora.fight.condition.DistanceCondition;
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.condition.health.GreaterHealthCondition;
import cc.js.sora.fight.skill.action.Haiwei;

public class ZillagodTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "古巨拉天赋";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return getEffects(1);
	}

	@Override
	public List<Effect> getEffects(int count) {
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, count * 3, Scope.All));
	}

	public Condition getCondition() {
		return new CounterCondition() {

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "损失士兵";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public int getMaxCount() {
				// TODO Auto-generated method stub
				return 10;
			}

			@Override
			public int getDefaultCount() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getCount(FightInfo fightInfo, boolean isAttack) {
				FightRole fightRole = fightInfo.getRole(isAttack);
				return Double.valueOf(Math.floor((fightRole.getSoldierPanel().getLife() - fightRole.getSoldierLeftLife())
								* 10.0 / fightRole.getSoldierPanel().getLife()))
						.intValue();
			}

		};
	}

	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "古巨拉*海德拉大心";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.CriticalProbDec, 10, Scope.All));
			}

			public Condition getCondition() {
				return new GreaterHealthCondition(80);
			}

		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "古巨拉*海德拉大心";
			}

			public Condition getCondition() {
				return new DistanceCondition(1);
			}

			public int getSkillType() {
				return 5;
			}

			@Override
			public List<Effect> getEffects() {
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
		}, new Haiwei(), new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "巨龙屏障";
			}
			
			public Condition getCondition() {
				return new UserCondition() {

					@Override
					public boolean defaultValid() {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "使用巨龙屏障";
					}
					
				};
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Feature(Feature.MagicToAttack,1.4, "魔防的1.4倍视为攻击", Scope.Hero, false));
			}
			
		});

	}
}
