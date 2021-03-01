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

public class LandiusTalent extends Skill{

	public long getId() {
		return Skill.LandiusTalent;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "兰迪乌斯天赋";
	}

	@Override
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
				return "周围3格有友军";
			}
			
		};
	}
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Attack,20,Scope.All));
	}
	
	public List<Skill> childSkill()
	{
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "兰迪乌斯*皇家骑士大心";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageInc, 10, Scope.All));
			}
			
			public int getSkillType()
			{
				return 4;
			}
			
		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "兰迪乌斯*皇家骑士大心";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.PhysicDamageDec, 10, Scope.All));
			}
			
			public int getSkillType()
			{
				return 5;
			}
			
		});
	}

}
