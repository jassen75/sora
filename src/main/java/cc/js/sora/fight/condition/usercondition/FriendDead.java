package cc.js.sora.fight.condition.usercondition;

import cc.js.sora.fight.condition.UserCondition;

public class FriendDead extends UserCondition {

	public String getName()
	{
		return "FriendDead";
	}
	
	@Override
	public boolean defaultValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return "当有队友死亡时";
	}

}
