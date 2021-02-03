package cc.js.sora.fight;

public interface Condition {
	
	
	public int getId();
	
	public String getTitle();
	
	public boolean valid(Hero attacker, Hero defender, Soldier attackerSolider, Soldier defenderSoldier, Environment env);
	
	public static Condition getContition(int id)
	{
		return new Condition() {

			@Override
			public int getId() {
				return 0;
			}

			@Override
			public String getTitle() {
				return "";
			}

			@Override
			public boolean valid(Hero attacker, Hero defender, Soldier attackerSolider, Soldier defenderSoldier,
					Environment env) {
				return true;
			}};
	}
	
	

}
