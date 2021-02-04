package cc.js.sora.fight.condition;

public interface ForceHealthCondition {
	
	
	
	boolean valid(int heroLife, int soldierLife, int heroLeftLife, int soldierLeftLife, int enemyHeroLife, int enemySoldierLife, int  enemyHeroLeftLife, int enemySoldierLeftLife);

}
