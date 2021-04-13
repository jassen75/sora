package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Jingmian extends Skill {

	@Override
	public String getName() {
		return "镜面铠甲";
	}
	
	@Override
	public Condition getCondition() {
		
		//return new EnemyFullHealthCondition();
		return new Condition() {

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "近战且不免疫固伤";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				if(isAttack)
				{
					return false;
				}
				if(fightInfo.getDistance() == 1) 
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
		return Lists.newArrayList(new Feature(Feature.PreFixDamagePhysic, 1.5, "战前1.5倍防御伤害", Scope.Hero, true));
	}
	
	@Override
	public int getBattleType() {
		return 1000;
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
			int damage = Double.valueOf(Math.floor(fightInfo.getDefender().getHeroPanel().getPhysic()*1.5)).intValue();
			this.dealFixDamage(fightInfo, !isAttack, damage, Scope.All);		
		}
    }

}
