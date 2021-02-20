package cc.js.sora.fight.condition;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;

public abstract class UserCondition implements Condition{


	public String getName()
	{
		return this.getClass().getName();
	}
	
	@JsonProperty("defaultValid")
	public abstract boolean defaultValid();

	@Override
	abstract public String getDesc();

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
		Map<String, Boolean> m = fightInfo.getRole(isAttack).getUserConditionChecked();
		if(m.containsKey(this.getName()))
		{
			return m.get(this.getName());
		} else
		{
			return this.defaultValid();
		}
	}
	
}
