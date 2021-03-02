package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;

public class Shuijingfengci extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "水晶锋刺";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.CriticalDamageInc, 10, Scope.Hero),
				new Enhance(BuffType.CriticalProbInc, 10, Scope.Hero));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "水晶锋刺";
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new Condition() {

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "技巧比对手高";
					}

					@Override
					public boolean valid(FightInfo fightInfo, boolean isAttack) {
						// TODO Auto-generated method stub
						return fightInfo.getRole(isAttack).getHeroPanel().getTech() > fightInfo.getEnemyRole(isAttack).getHeroPanel().getTech();
					}};
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.IgnoreDef, 20, Scope.Hero));
			}});
	}

}
