package cc.js.sora.fight.skill.heart;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.skill.talent.EluciaTalent;

public class EluciaHuangjia  extends Skill {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "伊露希亚*皇家卫士大心";
	}

	public int getSkillType() {
		return 4;
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
				return "伊露希亚*皇家卫士大心";
			}
			
			public int getSkillType() {
				return 4;
			}
			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 20, Scope.Soldier));
			}
			
		}, new EluciaTalent());
	}

}
