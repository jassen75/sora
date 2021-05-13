package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Counter;
import cc.js.sora.fight.Debuff;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.NoCondition;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Bujiechongji extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "不洁冲击";
	}
	
	public int getBattleType() {
		return 1000;
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new Condition() {

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "对面不免疫攻防降低";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				return (!fightInfo.getEnemyRole(isAttack).getHeroPanel().getFeatures()
						.containsKey(Feature.ImmuneToDebuff))
						&& (!fightInfo.getEnemyRole(isAttack).getHeroPanel().getFeatures()
								.containsKey(Feature.ImmuneToADReduce));
			}
			
		};
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Debuff(BuffType.Attack, -30), new Debuff(BuffType.Intel, -30),
				new Debuff(BuffType.Physic, -30), new Debuff(BuffType.Magic, -30), new Counter(BuffType.Intel, 30, 10, Scope.All));
	}

}
