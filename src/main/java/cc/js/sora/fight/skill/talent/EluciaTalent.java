package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Land;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.DistanceLessEqualCondition;
import cc.js.sora.fight.condition.NoCondition;
import cc.js.sora.fight.skill.action.Haiwei;

public class EluciaTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "伊露希亚天赋";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.PhysicDamageDec, 20, Scope.All));
	}
	
	public List<Skill> childSkill() {
		return Lists.newArrayList(new Haiwei(), new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "寒冰领域";
			}
			
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new DistanceLessEqualCondition(2);
			}

			public void process(FightInfo fightInfo, boolean isAttack) {
				if(this.getCondition().valid(fightInfo, isAttack))
				{
					fightInfo.getEnemyRole(isAttack).setLand(Land.Water);
				}
			}
			
			public int getBattleType() {
				return 0;
			}
			
			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Feature(Feature.EnemyLandAsWater, true, "地形视为水中", Scope.All, false));
			}
			
		});
	}

}
