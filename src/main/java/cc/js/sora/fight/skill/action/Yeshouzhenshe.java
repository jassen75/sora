package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Yeshouzhenshe extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "野兽震慑";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Feature(Feature.AddPhysicToAttack, 0.4, "防御40%增加到攻击", Scope.Hero, false),
				new Feature(Feature.AddMagicToAttack, 0.4, "魔防的40%增加到攻击", Scope.Hero, false));
	}

	public int getSkillType() {
		return 4;
	}

}
