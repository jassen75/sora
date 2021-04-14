package cc.js.sora.fight.skill.soldier;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.NoCondition;

public class Manzuyongshi extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "蛮族勇士";
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new Condition() {

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "部队生命百分比大于等于对面";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				// TODO Auto-generated method stub
				return fightInfo.getRole(isAttack).getLifePercent() >= fightInfo.getEnemyRole(isAttack).getLifePercent();
			}
			
		};
	}
	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 45, Scope.Soldier));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "蛮族勇士";
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new Condition() {

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "部队生命百分比小于对面";
					}

					@Override
					public boolean valid(FightInfo fightInfo, boolean isAttack) {
						// TODO Auto-generated method stub
						return fightInfo.getRole(isAttack).getLifePercent() < fightInfo.getEnemyRole(isAttack).getLifePercent();
					}
					
				};
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.Attack, 30, Scope.Soldier), new Enhance(BuffType.Physic, 30, Scope.Soldier));
			}
			
		});
	}

}
