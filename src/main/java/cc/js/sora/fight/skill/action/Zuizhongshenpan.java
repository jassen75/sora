package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Debuff;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.NoFeatureCondition;

public class Zuizhongshenpan extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "最终审判";
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new NoFeatureCondition(Feature.ImmuneToDebuff, "对面不免疫弱化");
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Debuff(BuffType.Range, -1));
	}
	
	public int getBattleType() {
		return 1000;
	}

}
