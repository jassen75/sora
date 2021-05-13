package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
 
public class Bazhe extends Skill{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "霸者勋章技能";
	}

	@Override
    public List<Effect> getEffects()
    {
    	return Lists.newArrayList(new Feature(Feature.ImmuneToADReduce, true, "免疫攻防降低", Scope.All, false));
    }

	@Override
    public void process(FightInfo fight, boolean isAttack)
    {
		fight.getRole(isAttack).getHeroPanel().getFeatures().put(Feature.ImmuneToADReduce, true);
    }
	
	@Override
	public int getBattleType()
	{
		return 0;
	}

}
