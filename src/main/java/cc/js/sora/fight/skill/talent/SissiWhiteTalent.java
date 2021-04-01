package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.FullHealthCondition;

public class SissiWhiteTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "怀特·茜茜天赋(仅对士兵)";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 20, Scope.Soldier), new Enhance(BuffType.Physic, 20, Scope.Soldier),
				new Enhance(BuffType.Magic, 20, Scope.Soldier), new Enhance(BuffType.Tech, 20, Scope.Soldier));
	}
	
	public List<Skill> childSkill()
	{
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "怀特·茜茜*军师大心";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageDec, 10, Scope.All), new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
			
			public Condition getCondition() {
				return new FullHealthCondition();
			}
			
		});
	}

}
