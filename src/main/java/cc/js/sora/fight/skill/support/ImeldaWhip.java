package cc.js.sora.fight.skill.support;

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

public class ImeldaWhip extends Skill {

	public long getId() {
		return Skill.ImeldaWhip;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "女王鞭子";
	}

	@Override
	public Condition getCondition() {
		return new UserCondition() {

			@Override
			public boolean getSupport() {
				return true;
			}

			@Override
			public String getDesc() {
				return "女王鞭子效果";
			}

			@Override
			public boolean defaultValid() {
				return false;
			}
		};
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Buff("ImeldaWhip",
				Lists.newArrayList(new Enhance(BuffType.Attack, 10, Scope.All),
						new Enhance(BuffType.Intel, 10, Scope.All), new Enhance(BuffType.DamageInc, 10, Scope.All),
						new Enhance(BuffType.Tech, 10, Scope.All), new Enhance(BuffType.CriticalProbInc, 10, Scope.All),
						new Enhance(BuffType.CriticalDamageInc, 10, Scope.All))));
	}

}
