package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.MoveDistanceCondition;
import cc.js.sora.fight.skill.action.Zhefan;

public class MysteryKnightTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "迷之骑士天赋";
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new MoveDistanceCondition(5,5);
	}

	// public abstract List<Buff> getBuffs();

	public int getSkillType() {
		return 1;
	}
	
	public List<Effect> getEffects()
	{
		return getEffects(5);
	}
	
	public List<Effect> getEffects(int count)
	{
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 4*count, Scope.All), new Enhance(BuffType.Magic, 20*count, Scope.All));
	}
	

	public List<Skill> childSkill() {
		return Lists.newArrayList(new Zhefan());
	}
	

}
