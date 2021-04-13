package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;


public class Beici extends Skill{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "背刺";
	}

	@Override
	public Condition getCondition() {
		
		return new UserCondition() {

			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "装备了背刺技能，对面满血且不免疫固伤";
			}
			
			public boolean needCheck()
			{
				return true;
			}
			
			public boolean check(FightInfo fightInfo, boolean isAttack) {
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
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Feature(Feature.PreFixDamageAttack, 2.0, "战前2倍攻击伤害", Scope.Hero, true));
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
			this.dealFixDamage(fightInfo, !isAttack, 2*fightInfo.getAttacker().getHeroPanel().getAttack(), Scope.All);		
		}
    }
}
