package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Debuff;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;

public class Jiwushen extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "姬武神";
	}

	public int getSkillType() {
		return 4;
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 30, Scope.Soldier));
	}

	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "姬武神";
			}

			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new UserCondition() {

					public boolean needCheck() {
						return true;
					}

					public boolean check(FightInfo fightInfo, boolean isAttack) {
						return !fightInfo.getEnemyRole(isAttack).getHeroPanel().getFeatures()
								.containsKey(Feature.ImmuneToDebuff)
								&& !fightInfo.getEnemyRole(isAttack).getHeroPanel().getFeatures()
										.containsKey(Feature.ImmuneToADReduce);
					}

					@Override
					public boolean defaultValid() {
						// TODO Auto-generated method stub
						return true;
					}

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "75%概率触发姬武神特效";
					}

				};
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Debuff(BuffType.Attack, -20), new Debuff(BuffType.Physic, -20));
			}

		});
	};

}
