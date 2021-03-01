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
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.condition.GroupedUserCondition;

public class ZillagodSuper extends Skill {

	public long getId() {
		return Skill.ZillagodSuper;
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new GroupedUserCondition() {
			
			@Override
			public boolean getSupport()
			{
				return true;
			}
			
			public String getName() 
			{
				return "ZillagodSuper";
			}

			public boolean needCheck() {
				return true;
			}

			public boolean check(FightInfo fightInfo, boolean isAttack) {
				return fightInfo.getRole(isAttack).getLifePercent() > fightInfo.getEnemyRole(isAttack).getLifePercent();
			}

			@Override
			public boolean defaultValid() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "光之起源队友，生命百分比高于对面";
			}

			@Override
			public String getGroupName() {
				// TODO Auto-generated method stub
				return "SuperBuff";
			}

		};
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "古巨拉超绝";
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(
				new Buff("ZillagodSuper", Lists.newArrayList(new Enhance(BuffType.DamageInc, 15, Scope.All))));
	}

	public int getSkillType() {
		return 4;
	}
	
	@Override
	public int getBattleType()
	{
		return 0;
	}
}
