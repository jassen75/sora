package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Counter;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Zhenshizijia extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "真十字架技能";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Counter(BuffType.Intel, 20, 8, Scope.All),
				new Counter(BuffType.Physic, 20, 8, Scope.All), new Counter(BuffType.Magic, 20, 8, Scope.All),
				new Counter(BuffType.Intel, 20, 9, Scope.All), new Counter(BuffType.Physic, 20, 9, Scope.All),
				new Counter(BuffType.Magic, 20, 9, Scope.All), new Enhance(BuffType.Medical,15, Scope.Hero));
	}

}
