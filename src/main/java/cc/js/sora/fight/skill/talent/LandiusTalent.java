package cc.js.sora.fight.skill.talent;

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
import cc.js.sora.fight.condition.DistanceGreaterThanCondition;
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.skill.action.Zhishui;

public class LandiusTalent extends Skill {

	public long getId() {
		return Skill.LandiusTalent;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "兰迪乌斯天赋";
	}

	@Override
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
				return "周围3格有友军";
			}

		};
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 20, Scope.All));
	}

	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "兰迪乌斯*皇家骑士大心";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
			}

			public int getSkillType() {
				return 4;
			}

		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "兰迪乌斯*皇家骑士大心";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.PhysicDamageDec, 10, Scope.All));
			}

			public int getSkillType() {
				return 5;
			}

		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "兰迪天赋";
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
						return "兰迪天赋BUFF存在（持续一回合）";
					}

				};
			}

			public void process(FightInfo fight, boolean isAttack) {
				fight.getRole(isAttack).getHeroPanel().getFeatures().put(Feature.AddAttackToDef, 15);
			}
			
			@Override
			public List<Effect> getEffects() {
				return Lists.newArrayList(new Feature(Feature.AddAttackToDef, 15, "攻击的15%增加到防御，魔防", Scope.Hero, false));
			}
			
			public int getBattleType() {
				return 0;
			}
		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "兰迪乌斯天赋";
			}

			public Condition getCondition() {
				return new DistanceGreaterThanCondition(1);
			}
			
			public int getSkillType() {
				return 2;
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageDec, 20, Scope.All),
						new Feature(Feature.RangeAttackBack, 2, "2格远程反击", Scope.All, false));
			}

		}, new Zhishui());
	}

}
