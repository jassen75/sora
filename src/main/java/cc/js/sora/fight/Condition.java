package cc.js.sora.fight;

public interface Condition {
	
	
//	public int getId();
//	
	public String getDesc();
	
	public boolean valid(FightInfo fightInfo, boolean isAttack);
//	
//	public boolean valid();
//	
//	public static Condition getContition(int id)
//	{
//		return new Condition() {
//
//			@Override
//			public int getId() {
//				return 0;
//			}
//
//			@Override
//			public String getTitle() {
//				return "";
//			}
//
//			@Override
//			public boolean valid() {
//				return true;
//			}};
//	}
	
	

}
