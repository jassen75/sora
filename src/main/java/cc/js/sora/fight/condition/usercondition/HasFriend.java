package cc.js.sora.fight.condition.usercondition;

import cc.js.sora.fight.condition.UserCondition;

public class HasFriend extends UserCondition{
	int count;
	
	public HasFriend(int count)
	{
		this.count = count;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return count+"格范围内有队友";
	}

	@Override
	public boolean defaultValid() {
		// TODO Auto-generated method stub
		return true;
	}

}
