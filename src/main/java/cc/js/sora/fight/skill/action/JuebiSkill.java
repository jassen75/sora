package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.Features;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.GroupedUserCondition;

public class JuebiSkill  extends Skill{

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "上古战阵.绝壁";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new GroupedUserCondition() {

			@Override
			public String getName()
			{
				return "UseJuebi";
			}

			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "主动使用绝壁";
			}

			@Override
			public String getGroupName() {
				// TODO Auto-generated method stub
				return "ShangGuZhanZhen";
			}};
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.PhysicDamageDec, 35, Scope.Soldier),
				new Enhance(BuffType.Physic, 15, Scope.All), new Enhance(BuffType.Magic, 15, Scope.All), 
				new Feature(Features.ImmuneToFixedDamage, true, "免疫固伤"));
	}
	
	@Override
	public int getBattleType()
	{
		return 0;
	}
}
