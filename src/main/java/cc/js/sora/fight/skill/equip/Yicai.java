package cc.js.sora.fight.skill.equip;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Action;
import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.NoDefLandConddition;

public class Yicai extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "逸才权杖技能";
	}
	
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new Condition() {

			@Override
			public String getDesc() {
				// TODO Auto-generated method stub
				return "处于非防御地形且使用技能时";
			}

			@Override
			public boolean valid(FightInfo fightInfo, boolean isAttack) {
				// TODO Auto-generated method stub
				Action action = fightInfo.getAttacker().getAction();
				if(action != null && !action.isSimpleAttack() && action.getBattleType()==1)
				{
					return new NoDefLandConddition().valid(fightInfo, isAttack);
				}
				
				return false;
			}
			
		};
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Range, 1, Scope.All));
	}
	
	public int getSkillType()
	{
		return 4;
	}

}
