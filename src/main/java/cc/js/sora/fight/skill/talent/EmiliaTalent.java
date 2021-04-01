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

public class EmiliaTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "艾米莉亚天赋";
	}
	

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.MagicDamageDec, 30, Scope.All));
	}

	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "高阶圣卫效果";
			}
			
			public Condition getCondition() {
				return new UserCondition() {
					
					public String getName()
					{
						return "Tianyu";
					}

					@Override
					public boolean defaultValid() {
						// TODO Auto-generated method stub
						return true;
					}

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "主动使用天御圣境";
					}
					
				};
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Feature(Feature.MagicToAttack,1.6, "魔防的1.6倍视为攻击", Scope.Hero, false));
			}
			
		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "公正对决";
			}
			
			public Condition getCondition() {
				return new Condition() {

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "血量超过90%且防御高于对面";
					}

					@Override
					public boolean valid(FightInfo fightInfo, boolean isAttack) {
						// TODO Auto-generated method stub
						return fightInfo.getRole(isAttack).getLifePercent()>0.9 && 
								(fightInfo.getRole(isAttack).getHeroPanel().getPhysic() > fightInfo.getEnemyRole(isAttack).getHeroPanel().getPhysic());
					}
					
				};
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Feature(Feature.FirstAttack, true, "先于敌人攻击", Scope.All, false));
			}
			
		},new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "天御圣境效果";
			}
			
			public Condition getCondition() {
				return new UserCondition() {
					
					public String getName()
					{
						return "Tianyu";
					}

					@Override
					public boolean defaultValid() {
						// TODO Auto-generated method stub
						return true;
					}

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "主动使用天御圣境";
					}
					
				};
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.CriticalDamageDec, 30, Scope.All));
			}
			
		});
	}

	
}
