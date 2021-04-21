package cc.js.sora.fight.skill.heart;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.EnemyHasDebuffCondition;
import cc.js.sora.fight.skill.talent.AlutemuratTalent;

public class AlutemuratSP extends Skill{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "亚鲁特缪拉*天穹魔龙大心";
	}
	
	public Condition getCondition() {
		return new EnemyHasDebuffCondition();
	}

	public int getSkillType() {
		return 5;
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 10, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "亚鲁特缪拉*天穹魔龙大心";
			}
			
			public int getSkillType() {
				return 3;
			}

			public Condition getCondition() {
				return new EnemyHasDebuffCondition();
			}
			
			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
			
		}, new AlutemuratTalent());
	}
}
