package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.DistanceGreaterThanCondition;

public class BozelTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "波赞鲁天赋";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Feature(Feature.MagicToIntel, 1.5, "魔防的1.5倍转换为智力", Scope.Hero, false));
	}
	
	public int getBattleType() {
		return 0;
	}
	
	public void process(FightInfo fight, boolean isAttack) {
		fight.getRole(isAttack).getHeroPanel().getFeatures().put(Feature.MagicToIntel, 1.5);
	}

	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "波赞鲁*黑暗王子大心";
			}

			public Condition getCondition() {
				return new DistanceGreaterThanCondition(1);
			}

			public int getSkillType() {
				return 5;
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.PhysicDamageDec, 10, Scope.All));
			}
		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "波赞鲁*黑暗王子大心";
			}
			
			public int getSkillType() {
				return 5;
			}

			public Condition getCondition() {
				return new Condition() {

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "受到魔法伤害";
					}

					@Override
					public boolean valid(FightInfo fightInfo, boolean isAttack) {
						// TODO Auto-generated method stub
						return fightInfo.getEnemyRole(isAttack).getHeroPanel().getAttackType() == 2;
					}};
			}

			@Override
			public List<Effect> getEffects() {
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
		});
	}

}
