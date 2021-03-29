package cc.js.sora.fight.skill.heart;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.EnemyHasDebuffCondition;
import cc.js.sora.fight.condition.health.GreaterHealthCondition;
import cc.js.sora.fight.skill.talent.RozencielTalent;

public class RozencielLingguang  extends Skill{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "罗泽希尔*灵光圣女大心";
	}
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.CriticalProbDec, 10, Scope.All));
	}

	public Condition getCondition() {
		return new GreaterHealthCondition(80);
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "罗泽希尔*灵光圣女大心";
			}

			public Condition getCondition() {
				return new EnemyHasDebuffCondition();
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
		}, new RozencielTalent());
	}
}
