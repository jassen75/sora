package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Debuff;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Wumianzhe extends Skill{
	
	public long getId() {
		return Skill.Wumianzhe;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "无面者技能";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 15, Scope.Soldier),new Enhance(BuffType.CriticalProbInc, 15, Scope.Soldier));
	}
	
	public List<Skill> childSkill()
	{
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "无面者技能";
			}
			
			public int getSkillType() {
				return 3;
			}

			// 0 effect 1 pre battle 2 battle 3 post battle 4
			public int getBattleType() {
				return 1;
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Debuff(BuffType.CriticalProbDec, -15));
			}
			
		});
	}

}
