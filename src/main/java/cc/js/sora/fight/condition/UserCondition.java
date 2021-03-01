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
	
	public boolean needCheck()
	{
		return false;
	}
	
	public boolean check(FightInfo fightInfo, boolean isAttack) {
		return true;
	}
	
	public boolean getSupport()
	{
		return false;
	}
	
	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
		boolean result = false;
		Map<String, Boolean> m = fightInfo.getRole(isAttack).getUserConditionChecked();
		if(m!= null && m.containsKey(getName()))
		{
			log.info(getName()+":"+m.get(getName()));
			result =  m.get(getName());
		} else
		{
			result = this.defaultValid();
		}
		if(result && needCheck())
		{
			return check(fightInfo, isAttack);
		}
		return result;
	}
	
}
