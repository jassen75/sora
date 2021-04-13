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
import cc.js.sora.fight.condition.UserCondition;

public class FreyaTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SP芙蕾雅天赋";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageDec, 15, Scope.All), new Enhance(BuffType.CriticalProbDec, 30, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "SP芙蕾雅天赋";
			}
			
			public int getSkillType() {
				return 2;
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
						return "2格范围内，对方是混合部队无法免疫";
					}

					@Override
					public boolean valid(FightInfo fightInfo, boolean isAttack) {
						
						if(fightInfo.getDistance() > 2)
						{
							return false;
						}
						if(!fightInfo.getEnemyRole(isAttack).isMix())
						{
							return !fightInfo.getEnemyRole(isAttack).getHeroPanel().getFeatures().containsKey("ImmuneToFixedDamage");
						}
						return false;
						// TODO Auto-generated method stub
					}
					
				};
			}
			
			@Override
		    public void process(FightInfo fightInfo, boolean isAttack)
		    {
				int damage = fightInfo.getDefender().getHeroPanel().getPhysic()+fightInfo.getDefender().getHeroPanel().getMagic();
				if(this.getCondition().valid(fightInfo, isAttack))
				{
					this.dealFixDamage(fightInfo, !isAttack, damage, Scope.All);		
				}
		    }

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Feature(Feature.PreFixDamage2Def, true, "战前防御+魔防固伤", Scope.Hero, false));
			}
			
		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "铁蔷薇";
			}
			
			public Condition getCondition() {
				return new UserCondition() {

					@Override
					public boolean defaultValid() {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "装备技能铁蔷薇";
					}
					
				};
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.MagicDamageDec, 20, Scope.All), 
						new Feature(Feature.MagicToPhysic, 0.8, "魔防的0.8倍代替防御", Scope.Hero, false));
			}
			
		});
	}

}
