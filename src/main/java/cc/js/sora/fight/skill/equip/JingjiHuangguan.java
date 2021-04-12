package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class JingjiHuangguan extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "荆棘皇冠";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Feature(Feature.PreFixDamageMagic, 2.0, "战前2倍魔防固定伤害", Scope.Hero, true));
	}
	
	public int getBattleType() {
		return 1;
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new Condition() {

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "对面不免疫固伤";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {

				return !fightInfo.getEnemyRole(isAttack).getHeroPanel().getFeatures().containsKey("ImmuneToFixedDamage");
			}
			
		};
	}
	
	@Override
    public void process(FightInfo fightInfo, boolean isAttack)
    {
		if(this.getCondition().valid(fightInfo, isAttack))
		{
			fightInfo.getAttacker().setHeroLeftLife(fightInfo.getAttacker().getHeroLeftLife() -
					2 * fightInfo.getDefender().getHeroPanel().getMagic());
			fightInfo.getAttacker().setSoldierLeftLife(fightInfo.getAttacker().getSoldierLeftLife() - 
					2* fightInfo.getDefender().getHeroPanel().getPhysic()+fightInfo.getDefender().getHeroPanel().getMagic());		
		}
    }

}
