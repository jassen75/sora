package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.Features;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Mingxiang extends Skill {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "冥想指环";
	}
	
	@Override
    public List<Effect> getEffects()
    {
    	return Lists.newArrayList(new Feature(Features.ImmuneToFixedDamage, true, "免疫固伤", Scope.All, false));
    }
	
	@Override
    public String description()
    {
    	return "免疫固伤";
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
