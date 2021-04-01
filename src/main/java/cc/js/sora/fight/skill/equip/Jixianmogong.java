package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Jixianmogong extends Skill{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "极限魔弓技能";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Feature(Feature.ImmuneToMeleeDamageReduce, true, "无视近战伤害减免", Scope.All, false));
	}

}
