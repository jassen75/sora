package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.LessHealthCondition;

public class ElwinTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "埃尔文天赋";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 25, Scope.All),
				new Enhance(BuffType.Physic, 25, Scope.All));
	}

	public int getSkillType() {
		return 1;
	}

	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "埃尔文*光龙统帅大心";
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
				return "埃尔文*光龙统帅大心";
			}

			public Condition getCondition() {
				return new LessHealthCondition(70);
			}

			@Override
			public List<Effect> getEffects() {
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
		});

	}

}
