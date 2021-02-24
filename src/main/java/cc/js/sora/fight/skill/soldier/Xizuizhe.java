package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Xizuizhe extends Skill{
	public long getId() {
		return Skill.Xizuizhe;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "洗罪者技能";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 15, Scope.Soldier),new Enhance(BuffType.DamageDec, 15, Scope.Soldier));
	}

}
