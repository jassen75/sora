package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.condition.health.FullHealthCondition;
import cc.js.sora.fight.condition.health.GreaterHealthCondition;

public class MarielTalent extends Skill {

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return Skill.MarielTalent;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "玛丽埃尔天赋";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new GreaterHealthCondition(50);
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 16, Scope.All),
				new Enhance(BuffType.DamageDec, 20, Scope.All));
	}

	@Override
	public int getSkillType() {
		return 1;
	}

	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "玛丽埃尔天赋";
			}

			@Override
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
						return "戒律存在";
					}

				};
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, -50, Scope.All),
						new Enhance(BuffType.DamageDec, 25, Scope.All), new Enhance(BuffType.Medical, 50, Scope.All));
			}

		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "圣怒";
			}

			@Override
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
						return "使用圣王直击以后获得圣怒";
					}

				};
			}
			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(
						new Buff("Shenglu", Lists.newArrayList(new Enhance(BuffType.DamageInc, 20, Scope.All))));
			}

		});
	}

}
