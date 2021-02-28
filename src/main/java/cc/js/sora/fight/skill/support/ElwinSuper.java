package cc.js.sora.fight.skill.support;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.GroupedUserCondition;


public class ElwinSuper extends Skill {
	public long getId() {
		return Skill.ElwinSuper;
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new GroupedUserCondition() {

			public boolean needCheck() {
				return true;
			}

			public boolean check(FightInfo fightInfo, boolean isAttack) {
				return fightInfo.getRole(isAttack).getLifePercent() > 80;
			}

			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "光辉军团队友，生命百分比超过80%";
			}

			@Override
			public String getGroupName() {
				// TODO Auto-generated method stub
				return "SuperBuff";
			}

		};
	}
	
	public boolean isSupportSkill()
	{
		return true;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "不灭的辉光";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(
				new Buff("ZillagodSuper", Lists.newArrayList(new Enhance(BuffType.DamageInc, 15, Scope.All))));
	}

	public int getSkillType() {
		return 3;
	}
	
	@Override
	public int getBattleType()
	{
		return 0;
	}

}
