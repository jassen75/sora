package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.CounterCondition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.skill.passivity.Chengfeng;

public class AresTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "阿雷斯天赋";
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new CounterCondition() {

			public String getName()
			{
				return "AresTalent";
			}
			
			@Override
			public int getMaxCount() {
				// TODO Auto-generated method stub
				return 5;
			}

			public int getDefaultCount() {
				return 2;
			}

			@Override
			public boolean defaultValid() {
				return true;
			}

			@Override
			public String getDesc() {
				return "周围三圈有敌人,最高叠加4次";
			}
		};
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return getEffects(2);
	}
	
	@Override
	public List<Effect> getEffects(int count) {
		return Lists.newArrayList(new Enhance(BuffType.Attack, count*5, Scope.All), new Enhance(BuffType.Physic, count*5, Scope.All),
				new Enhance(BuffType.Magic, count*5, Scope.All), new Enhance(BuffType.Tech, count*5, Scope.All));
	}
	
	public List<Skill> childSkill()
	{
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "阿雷斯*龙骑统帅大心";
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
						return "血量百分比少于70%";
					}

					@Override
					public boolean valid(FightInfo fightInfo, boolean isAttack) {
						// TODO Auto-generated method stub
						return fightInfo.getRole(isAttack).getLifePercent() < 0.7;
					}};
			}
			
			public int getSkillType()
			{
				return 3;
			}
			
		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "烈风再起";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.CriticalProbInc, 10, Scope.All));
			}
			
			
		},new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "无惧飓风";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Buff(BuffType.Attack, 20));
			}
			
		}, new Chengfeng() {
			
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "无惧飓风*乘风";
			}
			
		});
	}

}
