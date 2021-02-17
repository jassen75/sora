package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effects;
import cc.js.sora.fight.Fight;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.NoCondition;

public class Xunzhang extends Skill{

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return Skill.Xunzhang;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "铸剑者勋章技能";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new NoCondition();
	}

	@Override
	public List<Buff> getBuffs() {
		return Lists.newArrayList();
	}

	@Override
	public Scope getScope() {
		// TODO Auto-generated method stub
		return Scope.All;
	}
	
	@Override
    public List<String> getEffects()
    {
    	return Lists.newArrayList(Effects.ImmuneToFixedDamage);
    }
	
	@Override
    public String description()
    {
    	return "免疫固伤，免疫沉默";
    }
	
	@Override
    public void process(Fight fight, boolean isAttack)
    {
		if(isAttack)
		{
			fight.getAttackerEffects().add(Effects.ImmuneToFixedDamage);
		} else
		{
			fight.getDefenderEffects().add(Effects.ImmuneToFixedDamage);
		}
    	
    }
	
	@Override
	public int getBattleType()
	{
		return 0;
	}
    

}
