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
import cc.js.sora.fight.condition.UserCondition;

public class HildaTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "希尔达天赋";
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 25, Scope.All));
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new Condition() {

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "强化数量大于或者等于2";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				// TODO Auto-generated method stub
				return fightInfo.getRole(isAttack).getBuffs().size() >= 2;
			}

		};
	}

	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "希尔达*大元帅大心";
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
				return "希尔达*大元帅大心";
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
				return "希尔达天赋";
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
						return "希尔达天赋BUFF存在（持续一回合）";
					}
					
				};
			}
			
			@Override
			public List<Effect> getEffects() {
				return Lists.newArrayList(new Feature(Feature.AddAttackToDef, 15, "攻击的15%增加到防御，魔防", Scope.Hero, false));
			}
		});
	}

}
