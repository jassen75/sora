package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Senlinjisi extends Skill{
	
	public long getId() {
		return Skill.Senlinjisi;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "森林祭祀技能";
	}


	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Medical, 30, Scope.Soldier));
	}

}
