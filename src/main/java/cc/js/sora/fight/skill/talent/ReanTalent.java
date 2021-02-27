package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.CounterCondition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReanTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "黎恩天赋";
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new CounterCondition() {

			public String getName()
			{
				return "ReanTalent";
			}
			
			@Override
			public int getMaxCount() {
				// TODO Auto-generated method stub
				return 5;
			}

			public int getDefaultCount() {
				return 3;
			}

			@Override
			public boolean defaultValid() {
				return true;
			}

			@Override
			public String getDesc() {
				return "使用技能,最高叠加5次";
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
		return Lists.newArrayList(new Enhance(BuffType.Attack, count*4, Scope.All), new Enhance(BuffType.Physic, count*4, Scope.All),
				new Enhance(BuffType.Magic, count*4, Scope.All), new Enhance(BuffType.Tech, count*4, Scope.All));
	}
	
	public List<Skill> childSkill()
	{
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "黎恩*剑圣大心";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageDec, 10, Scope.All), new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
			
			public Condition getCondition() {
				return new Condition() {

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "血量低于50%";
					}

					@Override
					public boolean valid(FightInfo fightInfo, boolean isAttack) {
						// TODO Auto-generated method stub
						return fightInfo.getRole(isAttack).getLifePercent() < 0.5;
					}};
			}
			
		},new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "变身进入鬼人状态";
			}

			@Override
			public List<Effect> getEffects() {
				return Lists.newArrayList(new Enhance(BuffType.DamageDec, 30, Scope.All), new Enhance(BuffType.DamageInc, 30, Scope.All));
			}});
	}
}
