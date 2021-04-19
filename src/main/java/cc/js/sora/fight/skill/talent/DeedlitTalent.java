package cc.js.sora.fight.skill.talent;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.BuffType;
import cc.js.sora.fight.Condition;
import cc.js.sora.fight.CounterUserCondition;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Land;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.condition.UserCondition;

public class DeedlitTalent extends Skill {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "蒂德莉特";
	}

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new CounterUserCondition() {

			public String getName()
			{
				return "DeedlitTalent";
			}
			
			@Override
			public int getMaxCount() {
				// TODO Auto-generated method stub
				return 3;
			}

			public int getDefaultCount() {
				return 3;
			}

			@Override
			public boolean defaultValid() {
				return true;
			}

			@Override
			public String getDesc() {
				return "前一回合每走一格";
			}
		};
	}
	
	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return getEffects(3);
	}
	
	@Override
	public List<Effect> getEffects(int count) {
		// TODO Auto-generated method stub
		return Lists.newArrayList(new Enhance(BuffType.Intel, 8*count, Scope.All), new Enhance(BuffType.Physic, 8*count, Scope.All));
	}

	public List<Skill> childSkill() {
		return Lists.newArrayList(new Skill() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "精灵之森效果";
			}

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
						return "身上拥有精灵之森或精灵之舞效果";
					}
					
				};
			}
			
			public int getBattleType() {
				return 0;
			}
			
			public void process(FightInfo fightInfo, boolean isAttack) {
				fightInfo.getRole(isAttack).setLand(Land.Wood);
			}
			
			@Override
			public List<Effect> getEffects() {
				// TODO Auto-generated method stub
				return Lists.newArrayList(new Feature(Feature.LandAsWood, true, "地形视为树林", Scope.All, false));
			}
			
		});
	}
}
