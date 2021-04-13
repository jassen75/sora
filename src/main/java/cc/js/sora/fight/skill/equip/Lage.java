package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.Features;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.EnemyFullHealthCondition;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Lage extends Skill {

	@Override
	public long getId() {
		return Skill.Lage;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "拉格纳罗克技能";
	}

	@Override
	public Condition getCondition() {
		
		//return new EnemyFullHealthCondition();
		return new Condition() {

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "敌人满血且不免疫固伤";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				if(!isAttack)
				{
					return false;
				}
				if(fightInfo.getEnemyRole(isAttack).getLifePercent() == 1) 
				{
					return !fightInfo.getEnemyRole(isAttack).getHeroPanel().getFeatures().containsKey("ImmuneToFixedDamage");
				}
				return false;
			}
		};
	}

	@Override
	public List<Effect> getEffects()  {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Feature(Feature.PreFixDamageAttack, 1.0, "战前1倍攻击伤害", Scope.Hero, true));
	}

	
	@Override
	public int getBattleType() {
		return 1000;
	}
	
	@Override
	public int getSkillType()
	{
		return 4;
	}
	
	@Override
    public void process(FightInfo fightInfo, boolean isAttack)
    {
		if(this.getCondition().valid(fightInfo, isAttack))
		{
			log.info("lage is true");
			this.dealFixDamage(fightInfo, !isAttack, fightInfo.getAttacker().getHeroPanel().getAttack(), Scope.All);	
		}
    }
}
