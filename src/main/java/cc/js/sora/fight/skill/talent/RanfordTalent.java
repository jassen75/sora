package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.skill.action.Qishijingshen;

public class RanfordTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "兰芳特天赋";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList();
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Qishijingshen());
	}

}
