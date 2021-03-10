package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.GreaterHealthCondition;
import cc.js.sora.fight.condition.health.LessHealthCondition;

public class WernerTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.IgnoreDef, 20, Scope.All),
				new Enhance(BuffType.CriticalProbInc, 20, Scope.All),
				new Enhance(BuffType.CriticalDamageInc, 20, Scope.All));
	}

	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "迦游罗*净地末裔大心";
			}

			public Condition getCondition() {
				return new LessHealthCondition(50);
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
				return "迦游罗*净地末裔大心";
			}

			public Condition getCondition() {
				return new GreaterHealthCondition(80);
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.CriticalDamageInc, 10, Scope.All));
			}
		});
	}

}
