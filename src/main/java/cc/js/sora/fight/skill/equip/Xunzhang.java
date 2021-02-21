package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.Features;
import cc.js.sora.fight.FightInfo;
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
	public Scope getScope() {
		// TODO Auto-generated method stub
		return Scope.All;
	}
	
	@Override
    public List<Effect> getEffects()
    {
    	return Lists.newArrayList(new Feature(Features.ImmuneToFixedDamage, true, "免疫固伤"));
    }
	
	@Override
    public String description()
    {
    	return "免疫固伤，免疫沉默";
    }
	
	@Override
    public void process(FightInfo fight, boolean isAttack)
    {
		fight.getRole(isAttack).getHeroPanel().getFeatures().put(Features.ImmuneToFixedDamage, true);
    }
	
	@Override
	public int getBattleType()
	{
		return 0;
	}
    

}
