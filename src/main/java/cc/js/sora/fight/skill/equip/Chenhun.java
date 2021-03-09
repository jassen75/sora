package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Chenhun extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "晨昏之星";
	}
	
	public void process(FightInfo fightInfo, boolean isAttack) {
		if(this.getCondition().valid(fightInfo, isAttack))
		{
			int damage = Math.min(fightInfo.getRole(isAttack).getHeroPanel().getAttack(), fightInfo.getRole(isAttack).getHeroPanel().getIntel());
			fightInfo.getEnemyRole(isAttack).setHeroLeftLife(fightInfo.getEnemyRole(isAttack).getHeroLeftLife() - damage);
			fightInfo.getEnemyRole(isAttack).setSoldierLeftLife(fightInfo.getEnemyRole(isAttack).getSoldierLeftLife() - damage);
		}
	}


	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new Condition() {

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "混合部队无法免疫";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				// TODO Auto-generated method stub
				if(fightInfo.getEnemyRole(isAttack).isMix())
				{
					return true;
				} else
				{
					return !fightInfo.getEnemyRole(isAttack).getHeroPanel().getFeatures().containsKey("ImmuneToFixedDamage");
				}
			}};
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Feature(Feature.PreFixDamageChenhun, true, "战前造成攻击和智力较低数值的固定伤害", Scope.Hero, false));
	}
	
	public int getBattleType() {
		return 1;
	}

}
