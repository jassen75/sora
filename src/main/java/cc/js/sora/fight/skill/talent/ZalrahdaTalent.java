package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.DistanceCondition;
import cc.js.sora.fight.condition.NoCondition;
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.condition.health.FullHealthCondition;
import cc.js.sora.fight.condition.health.GreaterHealthCondition;
import cc.js.sora.fight.skill.action.BloodSwordAction;
import cc.js.sora.fight.skill.passivity.*;

public class ZalrahdaTalent extends Skill {

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return Skill.ZalrahdaTalent;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "白毛天赋";
	}

	@Override
	public Condition getCondition() {
		return new NoCondition();
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.CriticalProbInc, 20, Scope.All));
	}

	public List<Skill> childSkill() {
		return Lists.newArrayList(new ZalrahdaTalent2(), new BloodSwordAction(), new BloodBattle(), new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "泽瑞达*影大心";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageDec, 10, Scope.All));
			}

			public Condition getCondition() {
				return new FullHealthCondition();
			}

		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "泽瑞达*影大心";
			}

			public Condition getCondition() {
				return new Condition() {

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "受到魔法攻击";
					}

					@Override
					public boolean valid(FightInfo fightInfo, boolean isAttack) {
						return !fightInfo.getEnemyRole(isAttack).getAction().isPhysic();
					}

				};
			}

			public int getSkillType() {
				return 5;
			}

			@Override
			public List<Effect> getEffects() {
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
		});
	}

}
