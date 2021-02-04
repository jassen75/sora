package cc.js.sora.fight.condition;

import com.fasterxml.jackson.annotation.JsonProperty;

import cc.js.sora.fight.Condition;

public interface UserCondition extends Condition{


	String getName();
	
	@JsonProperty("defaultValid")
	boolean defaultValid();
}
