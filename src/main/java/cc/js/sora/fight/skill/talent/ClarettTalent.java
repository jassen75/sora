package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.skill.action.Yazhi;
import cc.js.sora.fight.skill.passivity.Chengfeng;

public class ClarettTalent extends Skill{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "克拉雷特天赋";
	}

	public int getSkillType() {
		return 4;
	}
	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 20, Scope.All), new Enhance(BuffType.DamageDec, 20, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Chengfeng(), new Yazhi());
	}


}
