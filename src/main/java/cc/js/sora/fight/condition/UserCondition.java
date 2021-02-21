package cc.js.sora.fight.condition;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		if(m!= null && m.containsKey(getName()))
		{
			log.info(getName()+":"+m.get(getName()));
			return m.get(getName());
		}
		return this.defaultValid();
	}
	
}
