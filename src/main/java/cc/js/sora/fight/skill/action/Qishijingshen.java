package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.Features;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;

public class Qishijingshen extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "骑士精神";
	}
	public int getBattleType() {
		return 0;
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new UserCondition() {

			public String getName()
			{
				return "Qishijingshen";
			}
			
			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "主动开骑士精神";
			}
			
		};
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Buff(BuffType.Attack, 30), new Feature(Feature.ImmuneToDebuff, true, "免疫弱化效果", Scope.All, false) );
	}
	
	@Override
    public void process(FightInfo fightInfo, boolean isAttack)
    {
		if(this.getCondition().valid(fightInfo, isAttack))
		{
			fightInfo.getRole(isAttack).getHeroPanel().getFeatures().put(Feature.ImmuneToDebuff, true);
		} else
		{
			fightInfo.getRole(isAttack).getHeroPanel().getFeatures().put(Feature.ImmuneToDebuff, false);
		}
    }
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "气浪";
			}

			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new UserCondition() {

					public String getName()
					{
						return "Qishijingshen";
					}
					
					@Override
					public boolean defaultValid() {
						// TODO Auto-generated method stub
						return true;
					}

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "主动开骑士精神";
					}
					
				};
			}
			
			public int getSkillType() {
				return 4;
			}
			
			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return new Qilang().getEffects();
			}
			
		});
	}

}
