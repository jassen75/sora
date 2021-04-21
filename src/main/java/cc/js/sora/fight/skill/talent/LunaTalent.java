package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.skill.action.Zaiyidong;

public class LunaTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "露娜天赋";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Magic, 20, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "风之缠绕";
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new UserCondition() {

					@Override
					public boolean defaultValid() {
						// TODO Auto-generated method stub
						return true;
					}

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "拥有风之缠绕效果";
					}
					
				};
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Feature(Feature.MagicToAttack, 1.5, "魔防的1.5倍视为攻击", Scope.Hero, false));
			}
			
		}, new Zaiyidong());
	}

}
