package cc.js.sora.fight;

import java.util.Map;

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
public class Fight {
	
	long attackerHeroId;
	
	long defenderHeroId;

	long attackerSoldierId;
	
	long defenderSoldierId;
	
	int attackerAttack;
	int attackerPhysicDef;
	int attackerLife;
	int attackerMagicDef;
	int attackerIntel;
	int attackerTech;
	
	int defenderAttack;
	int defenderPhysicDef;
	int defenderLife;
	int defenderMagicDef;
	int defenderIntel;
	int defenderTech;
	
	int attackerHeroLeftLife;
	int attackerSoldierLeftLife;
	int defenderHeroLeftLife;
	int defenderSoldierLeftLife;
	
	int attackerSoldierLife;
	int defenderSoldierLife;
	
	int attackerSoldierAttack;
	int attackerSoldierPhysicDef;
	int attackerSoldierMagicDef;

	int defenderSoldierAttack;
	int defenderSoldierPhysicDef;
	int defenderSoldierMagicDef;
	
	Map<String, Boolean> attackerUserConditionChecked;
	Map<String, Boolean> defenderUserConditionChecked;

}
