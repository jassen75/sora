package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.condition.usercondition.FriendDead;
import cc.js.sora.fight.condition.usercondition.HasFriend;

public class YuusukeTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "浦饭幽助天赋";
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new HasFriend(2);
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack, 20, Scope.All),
				new Enhance(BuffType.Physic, 20, Scope.All));
	}

	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "浦饭幽助天赋";
			}

			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new FriendDead();
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.Attack, 10, Scope.All),
						new Enhance(BuffType.Physic, 10, Scope.All), new Enhance(BuffType.Magic, 10, Scope.All),
						new Enhance(BuffType.Tech, 10, Scope.All), new Enhance(BuffType.Range, 1, Scope.All));
			}

		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "浦饭变身";
			}
			
			public int getSkillType() {
				return 4;
			}
			
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new UserCondition() {
					
					public String getName()
					{
						return "YuusukeTalentChange";
					}

					@Override
					public boolean defaultValid() {
						// TODO Auto-generated method stub
						return true;
					}

					@Override
					public String getDesc() {
						// TODO Auto-generated method stub
						return "浦饭变身";
					}
				};
			}

			@Override
			public List<Effect> getEffects() {
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 30, Scope.All));
			}
			
		});
	}
}
