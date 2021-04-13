package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.DefLandCondition;
import cc.js.sora.fight.condition.NoCondition;

public class Yazhisheji extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "压制射击";
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new Condition() {
			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				if(!isAttack)
				{
					return false;
				}
				if(!new DefLandCondition().valid(fightInfo, isAttack))
				{
					return false;
				}

				return !fightInfo.getEnemyRole(isAttack).getHeroPanel().getFeatures().containsKey("ImmuneToFixedDamage");
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "防御地形且敌人不免疫固伤";
			}
			
		};

	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Feature(Feature.PreFixDamageAttack, 1.0, "战前1倍攻击伤害", Scope.Hero, true));
	}
	
	@Override
    public void process(FightInfo fightInfo, boolean isAttack)
    {
		if(this.getCondition().valid(fightInfo, isAttack))
		{
			this.dealFixDamage(fightInfo, !isAttack, fightInfo.getAttacker().getHeroPanel().getAttack(), Scope.All);	
		}
    }

}
