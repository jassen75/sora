package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Debuff;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.MoveDistanceCondition;
import cc.js.sora.fight.condition.NoFeatureCondition;

public class Pomiezhimao extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "破灭之矛";
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new MoveDistanceCondition(5,5);
	}

	public int getSkillType() {
		return 4;
	}
	
	public List<Effect> getEffects()
	{
		return getEffects(5);
	}
	
	public List<Effect> getEffects(int count)
	{
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10*count, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {
			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Debuff(BuffType.Random, 0));
			}
			
			public int getSkillType() {
				return 4;
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new NoFeatureCondition(Feature.ImmuneToDebuff, "对面不免疫弱化");
			}

			// 0 effect 1 pre battle 2 battle 3 post battle 4
			public int getBattleType() {
				return 1000;
			}

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "破灭之矛";
			}
			
		});
	}
}
