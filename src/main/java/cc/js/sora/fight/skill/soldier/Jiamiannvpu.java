package cc.js.sora.fight.skill.soldier;

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

public class Jiamiannvpu extends Skill {

	public long getId() {
		return Skill.Jiamiannvpu;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "假面女仆技能";
	}
	
	public Condition getCondition() 
	{
		return new Condition() {

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "自身没有装备勋章";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				// TODO Auto-generated method stub
				return !fightInfo.getRole(isAttack).getHeroPanel().getFeatures().containsKey("ImmuneToFixedDamage");
			}
			
		};
	}
	
	public int getBattleType() {
		return 1;
	}

	public void process(FightInfo fight, boolean isAttack) {
		
		if(!fight.getEnemyRole(isAttack).getHeroPanel().getFeatures().containsKey(Features.ImmuneToFixedDamage))
		{
			double result = Math.ceil(fight.getRole(isAttack).getSoldierLeftLife() * 0.95);
			fight.getRole(isAttack).setSoldierLeftLife(Double.valueOf(result).intValue());
		}
	}

	public int getSkillType() {
		return 3;
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Feature("FixDamageToSelf", 5, "士兵损失5%血量", Scope.Soldier, true));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				return "假面女仆技能";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.Attack, 45, Scope.Soldier),
						new Enhance(BuffType.Physic, 45, Scope.Soldier), new Enhance(BuffType.Magic, 45, Scope.Soldier));
			}
			
			public int getBattleType() {
				return 1;
			}
			
		});
	}

}
