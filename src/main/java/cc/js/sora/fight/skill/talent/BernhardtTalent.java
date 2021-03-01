package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Debuff;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.DistanceCondition;
import cc.js.sora.fight.condition.DistanceLessEqualCondition;
import cc.js.sora.fight.condition.UserCondition;

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
				return new DistanceCondition(1);
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
				return 1;
			}

		});
	}
}
