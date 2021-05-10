package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Land;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.SpecialLandCondition;

public class MuTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "茉天赋";
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new SpecialLandCondition(Lists.newArrayList(Land.Grass, Land.Wood));
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 15, Scope.All),
				new Enhance(BuffType.Intel, 15, Scope.All));
	}

	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "复苏之力";
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new SpecialLandCondition(Lists.newArrayList(Land.Grass, Land.Wood));
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageDec, 20, Scope.All));
			}
			
		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "复苏之力";
			}
			
			
			public int getSkillType() {
				return 4;
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new Condition() {

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "草地或者树林上使用技能";
					}

					@Override
					public boolean valid(FightInfo fightInfo, boolean isAttack) {
						// TODO Auto-generated method stub
						return new SpecialLandCondition(Lists.newArrayList(Land.Grass, Land.Wood)).valid(fightInfo, isAttack)
								&& !fightInfo.getRole(isAttack).getAction().isSimpleAttack();
					}
					
				};
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageDec, 20, Scope.All));
			}
			
		});
	}

}
