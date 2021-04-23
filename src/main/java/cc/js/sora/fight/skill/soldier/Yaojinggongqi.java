package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.Land;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.SpecialLandCondition;

public class Yaojinggongqi extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "妖精弓骑兵";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 20, Scope.Soldier));
	}

	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}

			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new SpecialLandCondition(Lists.newArrayList(Land.Wood, Land.Mountain, Land.Grass));
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.Attack, 15, Scope.Soldier),
						new Enhance(BuffType.MagicDamageDec, 30, Scope.Soldier),
						new Feature(Feature.ImmuneToMeleeDamageReduce, true, "免疫近战伤害减免", Scope.All, false));
			}

		});
	}

}
