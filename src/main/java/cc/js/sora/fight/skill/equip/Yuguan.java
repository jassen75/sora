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

public class Yuguan extends Skill {

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return Skill.Yuguan;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "尼约德的羽冠技能";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new Condition() {

			@Override
			public String getDesc() {
				return "生命比对方高";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				return fightInfo.getRole(isAttack).getHeroLeftLife() + fightInfo.getRole(isAttack)
						.getSoldierLeftLife() > fightInfo.getEnemyRole(isAttack).getSoldierLeftLife()
								+ fightInfo.getEnemyRole(isAttack).getHeroLeftLife();
			}
		};
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.PhysicDef, 15, Scope.Hero),
				new Enhance(BuffType.MagicDef, 15, Scope.Hero));
	}

}
