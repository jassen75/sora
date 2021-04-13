package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.CounterUserCondition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Shouhuzhe extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "守护者的追忆";
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new CounterUserCondition() {

			public String getName()
			{
				return "HasMateIn2";
			}
			
			@Override
			public int getMaxCount() {
				return 3;
			}

			public int getDefaultCount() {
				return 1;
			}

			@Override
			public boolean defaultValid() {
				return true;
			}

			@Override
			public String getDesc() {
				return "周围2格有队友";
			}
			
			public int getCount(FightInfo fightInfo, boolean isAttack)
			{
				return fightInfo.getDistance()-1;
			}
		};
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return getEffects(1);
	}
	
	@Override
	public List<Effect> getEffects(int count) {
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, count*5, Scope.All));
	}

}
