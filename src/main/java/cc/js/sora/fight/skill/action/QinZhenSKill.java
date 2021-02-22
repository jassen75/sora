package cc.js.sora.fight.skill.action;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.GroupedUserCondition;
import cc.js.sora.fight.condition.UserCondition;

public class QinZhenSKill extends Skill {

	public static final UserCondition QinZhenCondition = new GroupedUserCondition() {
		@Override
		public String getName() {
			return "UseQinzhen";
		}

		@Override
		public boolean defaultValid() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public String getDesc() {
			// TODO Auto-generated method stub
			return "主动使用侵阵";
		}

		@Override
		public String getGroupName() {
			// TODO Auto-generated method stub
			return "ShangGuZhanZhen";
		}
	};

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return Skill.Qinzhen;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "上古战阵.侵阵";
	}

	@Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return QinZhenCondition;
	}

	@Override
	public List<Effect> getEffects() {
		return Lists.newArrayList(new Enhance(BuffType.PhysicDamageDec, 35, Scope.Soldier),
				new Enhance(BuffType.Attack, 15, Scope.All), new Enhance(BuffType.Intel, 15, Scope.All));
	}

	@Override
	public int getBattleType() {
		return 0;
	}
	
	@Override
	public List<Skill> childSkill()
	{
		return Lists.newArrayList(new QinzhenSkill2());
	}
}
