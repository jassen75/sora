package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.HasBuffCondition;
import cc.js.sora.fight.skill.action.JuebiSkill;
import cc.js.sora.fight.skill.action.QinZhenSKill;
import cc.js.sora.fight.skill.action.QinzhenSkill2;
import cc.js.sora.fight.skill.heart.TowaHeart1;
import cc.js.sora.fight.skill.heart.TowaHeart2;

public class TowaTalent extends Skill  {

	@Override
	public long getId() {
		return Skill.TowaTalent;
	}

	@Override
	public String getName() {
		return "托娃天赋";
	}

	@Override
	public Condition getCondition() {
		return new HasBuffCondition();
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.Attack, 20, Scope.All), new Enhance(BuffType.Physic, 20, Scope.All));
	}
	
	public List<Skill> childSkill()
	{
		return Lists.newArrayList(new TowaHeart1(), new TowaHeart2(), new QinZhenSKill(), new JuebiSkill());
	}
	


}
