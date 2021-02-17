package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effects;
import cc.js.sora.fight.Fight;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.EnemyFullHealthCondition;

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
		
		return new EnemyFullHealthCondition();
	}

	@Override
	public List<Buff> getBuffs() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Buff(BuffType.PreBattleDamage, 1));
	}

	@Override
	public Scope getScope() {
		// TODO Auto-generated method stub
		return Scope.Hero;
	}
	
	@Override
	public int getBattleType() {
		return 1;
	}
	
	@Override
	public int getSkillType()
	{
		return 1;
	}
	
	@Override
    public void process(Fight fight, boolean isAttack)
    {
		if(isAttack)
		{
			if(!fight.getDefenderEffects().contains(Effects.ImmuneToFixedDamage))
			{
		    	fight.setDefenderHeroLeftLife(fight.getDefenderHeroLeftLife() - fight.getAttackerAttack());
		    	fight.setDefenderSoldierLeftLife(fight.getDefenderHeroLeftLife() - fight.getAttackerAttack());		
			}
		}
    }
}
