package cc.js.sora.fight.condition.usercondition;

import cc.js.sora.fight.condition.UserCondition;

public class AttackBeGuarded extends UserCondition {

	public String getName()
	{
		return "AttackBeGuarded";
	}
	
	@Override
	public boolean defaultValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return "攻击被护卫";
	}

}
