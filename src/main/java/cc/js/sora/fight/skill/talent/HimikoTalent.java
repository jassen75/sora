package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class HimikoTalent extends Skill {

	@Override
	public String getName() {
		return "西米果天赋";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.CriticalProbInc, 30, Scope.All),
				new Enhance(BuffType.CriticalDamageInc, 30, Scope.All));
	}
	
	public List<Skill> childSkill()
	{
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "西米果*幻王丸大心";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageDec, 10, Scope.All));
			}
			
			public Condition getCondition() {
				return new Condition() {

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "血量百分比比对面少";
					}

					@Override
					public boolean valid(FightInfo fightInfo, boolean isAttack) {
						// TODO Auto-generated method stub
						return fightInfo.getRole(isAttack).getLifePercent() < fightInfo.getEnemyRole(isAttack).getLifePercent();
					}};
			}
			
			public int getSkillType()
			{
				return 5;
			}
			
		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "西米果*幻王丸大心";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.Hero));
			}
			
			public int getSkillType()
			{
				return 6;
			}
			
		});
	}

}
