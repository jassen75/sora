package cc.js.sora.fight.skill.heart;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.NoCondition;
import cc.js.sora.fight.condition.health.GreaterHealthCondition;
import cc.js.sora.fight.skill.talent.HelenaTalent;
import cc.js.sora.fight.skill.talent.MuTalent;

public class MuMuxianzhe  extends Skill {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "茉*木贤者大心";
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new GreaterHealthCondition(80);
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.CriticalProbDec, 10, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "茉*木贤者大心";
			}
			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.Medical, 10, Scope.All));
			}
			
		}, new MuTalent());
	}

}
