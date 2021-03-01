package cc.js.sora.fight.condition;

public abstract class GroupedUserCondition extends UserCondition {

	@Override
	public abstract boolean defaultValid() ;

	@Override
	public abstract String getDesc();
	
	public abstract String getGroupName();

}
