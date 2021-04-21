package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.CounterUserCondition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.DistanceCondition;
import cc.js.sora.fight.condition.NoCondition;
import cc.js.sora.fight.condition.UserCondition;

public class AlutemuratTalent extends Skill{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "亚鲁特缪拉天赋";
	}

	public Condition getCondition() {
		return new CounterUserCondition() {
			
			public String getName() {
				return "AlutemuratTalent";
			}

			@Override
			public int getMaxCount() {
				// TODO Auto-generated method stub
				return 6;
			}

			@Override
			public int getDefaultCount() {
				// TODO Auto-generated method stub
				return 4;
			}

			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "回合开始时和进入战斗前无双";
			}
		};
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return getEffects(4);
	}
	
	@Override
	public List<Effect> getEffects(int count) {

		return Lists.newArrayList(new Enhance(BuffType.Attack, 8*count, Scope.All), new Enhance(BuffType.PhysicDamageDec, 5, Scope.All));

	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "天穹龙翼";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Feature(Feature.preHealPercent,  30, "战前回血30%", Scope.All, true));
			}
			
			public Condition getCondition() {
				return new UserCondition() {

					@Override
					public boolean defaultValid() {
						// TODO Auto-generated method stub
						return true;
					}

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "装备技能天穹龙翼";
					}
					
					public boolean needCheck()
					{
						return true;
					}
					
					public boolean check(FightInfo fightInfo, boolean isAttack) {
						return new DistanceCondition(1).valid(fightInfo, isAttack);
					}
					
				};
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public int getBattleType() {
				return 1005;
			}
			
			@Override
			public int getSkillType()
			{
				return 3;
			}
			
			@Override
		    public void process(FightInfo fightInfo, boolean isAttack)
		    {
				if(this.getCondition().valid(fightInfo, isAttack))
				{
					int hlf = fightInfo.getRole(isAttack).getHeroLeftLife() + Double.valueOf(fightInfo.getRole(isAttack).getHeroPanel().getLife() * 0.3).intValue();
					if(hlf > fightInfo.getRole(isAttack).getHeroPanel().getLife())
					{
						hlf = fightInfo.getRole(isAttack).getHeroPanel().getLife();
					}
					int slf = fightInfo.getRole(isAttack).getSoldierLeftLife() + Double.valueOf(fightInfo.getRole(isAttack).getSoldierPanel().getLife() * 0.3).intValue();
					if(slf > fightInfo.getRole(isAttack).getSoldierPanel().getLife())
					{
						slf = fightInfo.getRole(isAttack).getSoldierPanel().getLife();
					}
					fightInfo.getRole(isAttack).setHeroLeftLife(hlf);
					fightInfo.getRole(isAttack).setSoldierLeftLife(slf);		
				}
		    }
			
			
		});
	}

}
