package cc.js.sora.fight.skill.heart;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.skill.talent.LedynTalent;

public class LedynWangzhe  extends Skill {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "雷丁*王者大心";
	}
	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
	}
	
	public int getSkillType()
	{
		return 4;
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "雷丁*王者大心";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.PhysicDamageDec, 10, Scope.All));
			}
			
			public int getSkillType()
			{
				return 5;
			}
			
		}, new LedynTalent());
	}

}
