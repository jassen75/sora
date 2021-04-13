package cc.js.sora.fight;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FightInfo {
	
	
	FightRole attacker;
	FightRole defender;
	int distance;
//	
//	//0 init  1 calculate inc/jjc 2 build user conditions 3 calculate panel 
//	int stage = 0;
//	
	@JsonIgnore
	public FightRole getRole(boolean isAttack)
	{
		return isAttack ? attacker : defender;
	}
	
	@JsonIgnore
	public FightRole getEnemyRole(boolean isAttack)
	{
		return isAttack ? defender : attacker;
	}
	
	public void clean()
	{
		this.attacker.getHeroPanel().getFeatures().clear();
		this.attacker.getSoldierPanel().getFeatures().clear();
		this.defender.getHeroPanel().getFeatures().clear();
		this.defender.getSoldierPanel().getFeatures().clear();
		
		this.attacker.getHeroPanel().getEnemyFeatures().clear();
		this.attacker.getSoldierPanel().getEnemyFeatures().clear();
		this.defender.getHeroPanel().getEnemyFeatures().clear();
		this.defender.getSoldierPanel().getEnemyFeatures().clear();
		

	}
	

}
