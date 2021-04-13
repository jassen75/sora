package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Chenshi extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "尘世之光";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Feature(Feature.preHealPercent,  10, "战前回血10%", Scope.All, true));
	}

	@Override
	public int getBattleType() {
		return 1005;
	}
	
	@Override
	public int getSkillType()
	{
		return 5;
	}
	
	@Override
    public void process(FightInfo fightInfo, boolean isAttack)
    {
		if(this.getCondition().valid(fightInfo, isAttack))
		{
			log.info("chenshi is true");
			int hlf = fightInfo.getDefender().getHeroLeftLife() + Double.valueOf(fightInfo.getDefender().getHeroPanel().getLife() * 0.1).intValue();
			if(hlf > fightInfo.getDefender().getHeroPanel().getLife())
			{
				hlf = fightInfo.getDefender().getHeroPanel().getLife();
			}
			int slf = fightInfo.getDefender().getSoldierLeftLife() + Double.valueOf(fightInfo.getDefender().getSoldierPanel().getLife() * 0.1).intValue();
			if(slf > fightInfo.getDefender().getSoldierPanel().getLife())
			{
				slf = fightInfo.getDefender().getSoldierPanel().getLife();
			}
			fightInfo.getDefender().setHeroLeftLife(hlf);
			fightInfo.getDefender().setSoldierLeftLife(slf);		
		}
    }
}
