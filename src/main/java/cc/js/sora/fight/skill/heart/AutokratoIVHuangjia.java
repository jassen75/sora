package cc.js.sora.fight.skill.heart;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.skill.talent.AutokratoIVTalent;

public class AutokratoIVHuangjia extends Skill {
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "奥托克拉托四世*皇家统帅大心";
	}

	public int getSkillType() {
		return 7;
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
				return "奥托克拉托四世*皇家统帅大心";
			}
			
			public int getSkillType() {
				return 6;
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
			
		}, new AutokratoIVTalent());
	}

}
