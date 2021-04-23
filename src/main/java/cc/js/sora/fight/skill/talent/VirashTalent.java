package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.health.LifePercentCondition;
import cc.js.sora.fight.skill.action.Yushui;

public class VirashTalent extends Skill{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "维拉久天赋";
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new LifePercentCondition(70,100);
	}
	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.DamageInc, 15, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "维拉久天赋";
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new LifePercentCondition(0,40);
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 50, Scope.All));
			}
			
		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "维拉久天赋";
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new LifePercentCondition(40,70);
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 30, Scope.All));
			}
			
		}, new Yushui());
	}

}
