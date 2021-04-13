package cc.js.sora.fight.skill.equip;

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
import cc.js.sora.fight.condition.NoCondition;
import cc.js.sora.fight.condition.NoFeatureCondition;

public class Cangbaizhizhang extends Skill{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "苍白之杖";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.SkillDamage, 15, Scope.Hero));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "苍白之杖 随机弱化效果";
			}

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
			
		});
	}

}
