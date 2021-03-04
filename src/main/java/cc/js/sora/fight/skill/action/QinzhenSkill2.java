package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.Features;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QinzhenSkill2 extends Skill {

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "上古战阵.侵阵";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return QinZhenSKill.QinZhenCondition;
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Feature(Feature.PreFixDamageAttack, 0.5, "战前0.5倍攻击伤害", Scope.Hero, true));
	}

	public int getSkillType() {
		return 1;
	}

	@Override
    public void process(FightInfo fightInfo, boolean isAttack)
    {
		if(isAttack)
		{
			if(!fightInfo.getDefender().getHeroPanel().getFeatures().containsKey(Features.ImmuneToFixedDamage))
			{
				
				fightInfo.getDefender().setHeroLeftLife(Double.valueOf(Math.floor(fightInfo.getDefender().getHeroLeftLife() -
						0.5*fightInfo.getAttacker().getHeroPanel().getAttack())).intValue());
				fightInfo.getDefender().setSoldierLeftLife(Double.valueOf(Math.floor(fightInfo.getDefender().getSoldierLeftLife() - 
						0.5*fightInfo.getAttacker().getHeroPanel().getAttack())).intValue());		
			}
			
			log.info("after qinzhen:"+fightInfo.getDefender().getSoldierLeftLife());
		}
    }

}
