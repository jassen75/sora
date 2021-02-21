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
import cc.js.sora.fight.condition.NoCondition;

public class ZalrahdaTalent1 extends Skill {

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return Skill.ZalrahdaTalent1;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "白毛天赋";
	}

	@Override
	public Condition getCondition() {
		return new NoCondition();
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.CriticalProbInc, 20, Scope.All));
	}

}
