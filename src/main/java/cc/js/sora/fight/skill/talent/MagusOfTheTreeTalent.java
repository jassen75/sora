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
import cc.js.sora.fight.condition.health.GreaterHealthCondition;

public class MagusOfTheTreeTalent extends Skill {
	
	@Override
	public String getName() {
		return "树之贤者天赋";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Intel, 20, Scope.All));
	}
	
	public Condition getCondition() {
		return new UserCondition() {

			@Override
			public String getDesc() {
				return "自身3格范围内有队友";
			}

			@Override
			public boolean defaultValid() {
				return true;
			}
		};
	}
	
	public List<Skill> childSkill()
	{
		
		return Lists.newArrayList(new Skill(){

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "树之贤者天赋";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList( new Feature(Feature.ImmuneToMeleeDamageReduce, true, "无视近战伤害减免", Scope.All));
			}}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "树之贤者*大贤者大心";
			}

			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Enhance(BuffType.DamageDec, 10, Scope.All));
			}
			
			public int getSkillType()
			{
				return 5;
			}
			
		}, new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "树之贤者*大贤者大心";
			}
			
			@Override
			public Condition getCondition() {
				// TODO Auto-generated method stub
				return new GreaterHealthCondition(80);
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
			
		});
	}

}
