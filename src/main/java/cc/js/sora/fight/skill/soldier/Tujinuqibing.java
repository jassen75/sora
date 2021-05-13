package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Debuff;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;

public class Tujinuqibing extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "突击弩骑兵";
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new UserCondition() {

			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return true;
			}

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
			public String getDesc() {
				// TODO Auto-generated method stub
				return "75%概率触发突击弩骑兵特效";
			}

		};
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Debuff(BuffType.Physic, -20));
	}

}
